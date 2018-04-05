package com.abc;

import com.abc.jpa.JsonType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@javax.persistence.Entity
@Table(name = "entity")
@TypeDef(name = "jsonb", typeClass = JsonType.class)
//@EntityListeners(AuditingEntityListener.class)
public class Entity implements Serializable {

    private static final long serialVersionUID = -3009157732242241606L;

    @Id
    private String id = UUID.randomUUID().toString();

    @Version
    private Long version = 0L;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;


    @Column(name = "kv")
    @Type(type = "jsonb")
    private Json kv;

    private Map<String, Json> children;

    protected Entity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Json getKv() {
        return kv;
    }

    public void setKv(Json kv) {
        this.kv = kv;
    }

    public Map<String, Json> getChildren() {
        return children;
    }

    public void setChildren(Map<String, Json> children) {
        this.children = children;
    }

    public void addChild(String key, Json child) {
        if(this.children == null)
            this.children = new HashMap<>();
        this.children.put(key, child);
    }

    public Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Entity(String name, String description, Json kv) {
        this.name = name;
        this.description = description;
        this.kv = kv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (!id.equals(entity.id)) return false;
        if (version != null ? !version.equals(entity.version) : entity.version != null) return false;
        if (name != null ? !name.equals(entity.name) : entity.name != null) return false;
        if (description != null ? !description.equals(entity.description) : entity.description != null) return false;
        if (kv != null ? !kv.equals(entity.kv) : entity.kv != null) return false;
        return children != null ? children.equals(entity.children) : entity.children == null;

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (kv != null ? kv.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "id='" + id + '\'' +
                ", version=" + version +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", kv=" + kv +
                ", children=" + children +
                '}';
    }
}