package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.ModelIdentifier;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the History type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Histories", type = Model.Type.USER, version = 1, authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class History implements Model {
  public static final QueryField ID = field("History", "id");
  public static final QueryField USERNAME = field("History", "username");
  public static final QueryField TIME = field("History", "time");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String username;
  private final @ModelField(targetType="String", isRequired = true) String time;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public String getUsername() {
      return username;
  }
  
  public String getTime() {
      return time;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private History(String id, String username, String time) {
    this.id = id;
    this.username = username;
    this.time = time;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      History history = (History) obj;
      return ObjectsCompat.equals(getId(), history.getId()) &&
              ObjectsCompat.equals(getUsername(), history.getUsername()) &&
              ObjectsCompat.equals(getTime(), history.getTime()) &&
              ObjectsCompat.equals(getCreatedAt(), history.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), history.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getUsername())
      .append(getTime())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("History {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("username=" + String.valueOf(getUsername()) + ", ")
      .append("time=" + String.valueOf(getTime()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static UsernameStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static History justId(String id) {
    return new History(
      id,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      username,
      time);
  }
  public interface UsernameStep {
    TimeStep username(String username);
  }
  

  public interface TimeStep {
    BuildStep time(String time);
  }
  

  public interface BuildStep {
    History build();
    BuildStep id(String id);
  }
  

  public static class Builder implements UsernameStep, TimeStep, BuildStep {
    private String id;
    private String username;
    private String time;
    public Builder() {
      
    }
    
    private Builder(String id, String username, String time) {
      this.id = id;
      this.username = username;
      this.time = time;
    }
    
    @Override
     public History build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new History(
          id,
          username,
          time);
    }
    
    @Override
     public TimeStep username(String username) {
        Objects.requireNonNull(username);
        this.username = username;
        return this;
    }
    
    @Override
     public BuildStep time(String time) {
        Objects.requireNonNull(time);
        this.time = time;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String username, String time) {
      super(id, username, time);
      Objects.requireNonNull(username);
      Objects.requireNonNull(time);
    }
    
    @Override
     public CopyOfBuilder username(String username) {
      return (CopyOfBuilder) super.username(username);
    }
    
    @Override
     public CopyOfBuilder time(String time) {
      return (CopyOfBuilder) super.time(time);
    }
  }
  

  public static class HistoryIdentifier extends ModelIdentifier<History> {
    private static final long serialVersionUID = 1L;
    public HistoryIdentifier(String id) {
      super(id);
    }
  }
  
}
