# Audit4j-Hibernate

Using hibernate extention for audit4j, you can audit hibernate entitities.

## Quick Start
Add Audit4j to your hibernate project. for maven projects just add below dependencies:
```xml
<dependency>
	<groupId>org.audit4j</groupId>
	<artifactId>audit4j-core</artifactId>
	<version>2.4.1</version>
</dependency>
<dependency>
	<groupId>org.audit4j</groupId>
	<artifactId>audit4j-hibernate</artifactId>
	<version>2.4.1</version>
</dependency>
```

Basic usage of audit4j hibernate extention would be,
```java
import org.audit4j.core.annotation.Audit;
...

@Entity
@Audit
public class Person {
     ...
}
```
