package com.snuppy;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import java.util.List;


public class Connection {
    private static SessionFactory sessionFactoryObj;

    public Connection() {
        sessionFactoryObj = buildSessionFactory();
    }


    private static SessionFactory buildSessionFactory() {
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();
        SessionFactory sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }

    public void insert(UsersEntity user) {
        Session sessionObj = null;
        try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            sessionObj.save(user);
            sessionObj.getTransaction().commit();
        } catch (Exception sqlException) {
            if (sessionObj != null)
                if (null != sessionObj.getTransaction()) {
                    sessionObj.getTransaction().rollback();
                }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
    }

    public List<UsersEntity> select() {
        Session sessionObj = null;
        try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            Criteria cr = sessionObj.createCriteria(UsersEntity.class);
            sessionObj.getTransaction().commit();
            return cr.list();
        } catch (Exception sqlException) {
            if (sessionObj != null)
                if (null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......");
                    sessionObj.getTransaction().rollback();
                }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
        return null;
    }

    public byte[] selectImg(int idUser) {
        Session sessionObj = null;
        try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            Criteria cr = sessionObj.createCriteria(UsersEntity.class);
            cr.add(Restrictions.eq("idUser", idUser));
            sessionObj.getTransaction().commit();
            List<UsersEntity> list = cr.list();
            if (list.size() == 1) {
                return list.get(0).getImageBlob();
            } else return null;
        } catch (Exception sqlException) {
            if (sessionObj != null)
                if (null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......");
                    sessionObj.getTransaction().rollback();
                }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
        return null;
    }

    public int selectActiveUsersCount() {
        Session sessionObj = null;
        try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            Criteria cr = sessionObj.createCriteria(UsersEntity.class);
            cr.add(Restrictions.isNotNull("imageBlob"));
            sessionObj.getTransaction().commit();
            return cr.list().size();
        } catch (Exception sqlException) {
            if (sessionObj != null)
                if (null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......");
                    sessionObj.getTransaction().rollback();
                }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
        return -1;
    }

    public int selectUsersCount() {
        Session sessionObj = null;
        try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            Criteria cr = sessionObj.createCriteria(UsersEntity.class);
            sessionObj.getTransaction().commit();
            return cr.list().size();
        } catch (Exception sqlException) {
            if (sessionObj != null)
                if (null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......");
                    sessionObj.getTransaction().rollback();
                }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
        return -1;
    }

    public boolean emailExist(String email) {
        Session sessionObj = null;
        try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            Criteria cr = sessionObj.createCriteria(UsersEntity.class);
            cr.add(Restrictions.eq("userEmail", email));
            sessionObj.getTransaction().commit();
            List<UsersEntity> list = cr.list();
            if (list.size() > 0) {
                return true;
            } else return false;
        } catch (Exception sqlException) {
            if (sessionObj != null)
                if (null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......");
                    sessionObj.getTransaction().rollback();
                }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
        return true;
    }

    public int getIdByEmail(String email) {
        Session sessionObj = null;
        try {
            sessionObj = sessionFactoryObj.openSession();
            sessionObj.beginTransaction();
            Criteria cr = sessionObj.createCriteria(UsersEntity.class);
            cr.add(Restrictions.eq("userEmail", email));
            sessionObj.getTransaction().commit();
            List<UsersEntity> list = cr.list();
            if (list.size() == 1) {
                return list.get(0).getIdUser();
            } else return -1;
        } catch (Exception sqlException) {
            if (sessionObj != null)
                if (null != sessionObj.getTransaction()) {
                    System.out.println("\n.......Transaction Is Being Rolled Back.......");
                    sessionObj.getTransaction().rollback();
                }
            sqlException.printStackTrace();
        } finally {
            if (sessionObj != null) {
                sessionObj.close();
            }
        }
        return -1;
    }


}