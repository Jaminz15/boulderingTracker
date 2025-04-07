package matc.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

/**
 * A generic DAO for Hibernate CRUD operations.
 */
public class GenericDao<T> {
    private Class<T> type;
    private final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Instantiates a new GenericDao.
     *
     * @param type the entity type (e.g., User, Gym, Climb)
     */
    public GenericDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Gets all entities.
     * @return the list of all entities
     */
    public List<T> getAll() {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<T> list = session.createSelectionQuery(query).getResultList();
        session.close();
        return list;
    }

    /**
     * Gets an entity by ID.
     * @param id the entity ID
     * @return the entity
     */
    public T getById(int id) {
        Session session = getSession();
        T entity = session.get(type, id);
        session.close();
        return entity;
    }

    /**
     * Deletes an entity.
     * @param entity the entity to be deleted
     */
    public void delete(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.remove(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Inserts an entity.
     * @param entity the entity to insert
     * @return the generated ID
     */
    public int insert(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        session.flush();
        transaction.commit();
        int id = (int) session.unwrap(Session.class).getIdentifier(entity);
        session.close();
        return id;
    }

    /**
     * Updates an entity.
     * @param entity the entity to update
     */
    public void update(T entity) {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.merge(entity);
        transaction.commit();
        session.close();
    }

    /**
     * Finds entities by a single property.
     * Example: `findByPropertyEqual("name", "East Side Boulders")`
     * @param propertyName the property name
     * @param value the value to search for
     * @return the list of matching entities
     */
    public List<T> findByPropertyEqual(String propertyName, Object value) {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        query.select(root).where(builder.equal(root.get(propertyName), value));
        List<T> items = session.createSelectionQuery(query).getResultList();
        session.close();
        return items;
    }

    /**
     * Finds entities by multiple properties.
     * @param propertyMap a map of property names and values
     * @return the list of matching entities
     */
    public List<T> findByPropertyEqual(Map<String, Object> propertyMap) {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);
        List<Predicate> predicates = new ArrayList<>();
        for (Map.Entry<String, Object> entry : propertyMap.entrySet()) {
            predicates.add(builder.equal(root.get(entry.getKey()), entry.getValue()));
        }
        query.select(root).where(builder.and(predicates.toArray(new Predicate[0])));
        List<T> items = session.createSelectionQuery(query).getResultList();
        session.close();
        return items;
    }

    /**
     * Finds entities (like Climb) by the user's Cognito Sub field.
     *
     * This assumes the entity has a `User` relationship mapped as "user",
     * and that User has a `cognitoSub` property.
     *
     * Example usage:
     *     findByUserCognitoSub("11cb25b0-9011-7034-8885-4f7ffb871fe0")
     *
     * @param cognitoSub the Cognito user sub to match
     * @return a list of entities associated with that user
     */
    public List<T> findByUserCognitoSub(String cognitoSub) {
        Session session = getSession();
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(type);
        Root<T> root = query.from(type);

        query.select(root)
                .where(builder.equal(root.get("user").get("cognitoSub"), cognitoSub));

        List<T> userEntities = session.createSelectionQuery(query).getResultList();
        session.close();
        return userEntities;
    }

    /**
     * Gets a Hibernate session.
     * @return a new Hibernate session
     */
    private Session getSession() {
        return SessionFactoryProvider.getSessionFactory().openSession();
    }
}