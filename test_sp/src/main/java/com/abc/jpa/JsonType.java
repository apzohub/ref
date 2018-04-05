package com.abc.jpa;

import com.abc.Json;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.SerializationException;
import org.hibernate.usertype.UserType;
import org.postgresql.util.PGobject;

import java.io.IOException;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

public class JsonType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.JAVA_OBJECT};
    }

    @Override
    public Class returnedClass() {
        return Json.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return (o != null) && o.equals(o1);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return (o != null)?o.hashCode():0;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SessionImplementor sessionImplementor, Object o)
            throws HibernateException, SQLException {
        PGobject pgo = (PGobject) resultSet.getObject(names[0]);
        if (pgo.getValue() != null) {
            try {
                return Json.fromJson(pgo.getValue());
            } catch (IOException e) {
                throw new HibernateException(e);
            }
        }

        return new HashMap();
    }

    @Override
    public void nullSafeSet(PreparedStatement ps, Object value, int idx, SessionImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            ps.setNull(idx, Types.OTHER);
        } else {
            try {
                ps.setObject(idx, ((Json)value).toJson(), Types.OTHER);
            } catch (IOException e) {
                throw new HibernateException(e);
            }
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null || !(o instanceof Map)) {
            return null;
        }

        Json ret = new Json();
        Json tempMap = (Json) o;
        tempMap.forEach((key, value) -> ret.put(key, value));

        return ret;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        Object deepCopy = deepCopy(o);

        if (!(deepCopy instanceof Serializable)) {
            throw new SerializationException(String.format("deepCopy of %s is not serializable", o), null);
        }

        return (Serializable) deepCopy;
    }

    @Override
    public Object assemble(Serializable serializable, Object o) throws HibernateException {
        return deepCopy(serializable);
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return deepCopy(o);
    }
}
