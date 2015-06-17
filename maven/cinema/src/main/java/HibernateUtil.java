import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by Oleg on 17.06.2015.
 */
public class HibernateUtil // TODO add createSessionFactory to MySqlDaoFactory?
{
    private static SessionFactory sessionFactory;
    private static ServiceRegistry serviceRegistry;

    public static SessionFactory createSessionFactory()
    {
        Configuration configuration = new Configuration();
        configuration.configure();
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return sessionFactory;
    }
}
