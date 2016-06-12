package db;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-05-21T12:28:52")
@StaticMetamodel(Users.class)
public class Users_ { 

    public static volatile SingularAttribute<Users, Integer> id;
    public static volatile SingularAttribute<Users, Integer> executed;
    public static volatile SingularAttribute<Users, String> session;
    public static volatile SingularAttribute<Users, String> command;
    public static volatile SingularAttribute<Users, String> user;
    public static volatile SingularAttribute<Users, Integer> isAlive;
    public static volatile SingularAttribute<Users, Date> updatedTime;

}