# om

A lightweight SQL library written over JDBC driver for JVM language. 

## Usage

### Object

The very first step that is to defining a DTO database object to inherit the `PO` object: 

```kotlin
data class Pet(
    override var id: Int? = null,
    var name: String? = null,
    var owner: String? = null,
    var species: String? = null,
    var sex: String? = null,
    var birth: Date? = null,
    var death: Date? = null
) : PO(id)
```

The field-name and the filed-type of the DTO object must be consis with the column and type of 
the database_table which your definition in the database.

You could use CamelCase naming convention in your DTO object to compat the database_table which is
using Snake_Case naming convention:

```kotlin
data class Pet(
    override var id: Int? = null,
    var pName: String? = null,      // corresponded to p_name
    var pOwner: String? = null,     // corresponded to p_owner
    var pSpecies: String? = null,   // correxponded to p_species
    var pSex: String? = null,       // correxponded to p_sex   
    var pBirth: Date? = null,       // correxponded to p_birth   
    var pDeath: Date? = null        // correxponded to p_death   
) : PO(id)
```

### Event

By using om, the following example would show you using SQL-DML sentence, just as simple as to
calling a kotlin function.

First you need to create a SQL-DML engine, that is the database_table `.pet` special engine:

```kotlin
const val PET_TABLE = ".pet"
val peng = Engine(Pet::class.java, PET_TABLE)
```

Now i gonna keep using the table `pet` in all the following example. \

Inquiry:

```kotlin
/**
 * inquire all data from `pet` and each row generate as an obejct pet
 * SELECT * FROM .pet;
 * 
 */
fun pselect1() {
    peng.selectall().execute()
        .forEach(::println)
}

/**
 * inquire the first row from `pet` that match by the given identity
 * SELECT * FROM .pet WHERE id = 1 LIMIT 1;
 */
fun pselect2() {
    peng.select1 { id = 1 }
        .run(::println)
}

/**
 * inquire by using a DML-like-DSL syntax which implement by kotlin `infix` function
 * SELECT * FROM .pet WHERE name REGEXP 'i' || sex = 'm' ORDER BY birth asc LIMIT 3 OFFSET 0;
 */
fun pselect3() {
    peng.selectf { "name" regexp "i" or "sex" eq "m" orderBy "birth asc" limit 3 offset 0 }
        .forEach(::println)
}

/**
 * inquire by a series function calling, this is a stream processing
 * SELECT * FROM .pet WHERE name = '%i%' && sex = 'm' ORDER BY id asc, birth asc LIMIT 3 OFFSET 0;
 */
fun pselect4() {
    peng.selectm { name = "%i%"; sex = "m" }
        .orderBy("id asc", "birth asc")
        .limit(3)
        .offset(0)
        .execute()
        .forEach(::println)
}
```

Insertion:

```kotlin
/**
 * insert a DTO object to table `.pet` according by the given DTO object
 * INSERT INTO .pet (name,owner,species,birth)VALUES('Frey','Ldc','giraffe','1997-05-06');
 */
fun pinsert() {
    peng.insert {
        name = "Frey"
        owner = "Ldc"
        species = "giraffe"
        birth = Date.valueOf("1997-05-06")
    }
}
```

Modification:

```kotlin
/**
 * change elements of a DTO object in table `.pet` according by the givien identity
 * UPDATE .pet SET id = 3, name = 'Gray' WHERE ID=3;
 */
fun pupdate() {
    peng.update {
        id = 3
        name = "Gray"
    }
}
```


Deletion:

```kotlin
/**\
 * remove a DTO object from table `.pet` according by the given identity 
 * DELETE FROM .pet WHERE id = 3;
 */
fun pdelete() {
    peng.delete {
        id = 3
    }
}
```

## Contribution

You could check out [here][core] to see the detail of some concrete implemention to find more 
interesting and convenient usage, and it also welcome to your contribute.

[core]: https://github.com/ldcc/om/blob/master/src/org/ldcc/om/core/DML.kt.