package db;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130918-rNA", date="2015-05-21T12:28:52")
@StaticMetamodel(LogRpc.class)
public class LogRpc_ { 

    public static volatile SingularAttribute<LogRpc, Integer> logId;
    public static volatile SingularAttribute<LogRpc, Date> insertTime;
    public static volatile SingularAttribute<LogRpc, String> user;
    public static volatile SingularAttribute<LogRpc, String> log;

}