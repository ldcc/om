# om

A lightweight SQL library written over JDBC driver for JVM language. 

## Usage

### Object

First step is to defined a DTO database object to inherit the `PO` object: 

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

Use CamelCase naming in your DTO object to compat the database_table to Snake_Case:

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

The following syntax as similar as using DML sentence.

First, create the DML engine for the database_table `.pet`:

```kotlin
const val PET_TABLE = ".pet"
val peng = Engine(Pet::class.java, PET_TABLE)
```

Query:

```kotlin
/**
 * SELECT * FROM .pet;
 */
fun pselect1() {
    peng.selectall().execute()
        .forEach(::println)
}

/**
 * SELECT * FROM .pet WHERE id = 1 LIMIT 1;
 */
fun pselect2() {
    peng.select1 { id = 1 }
        .run(::println)
}

/**
 * SELECT * FROM .pet WHERE name REGEXP 'i' || sex = 'm' ORDER BY birth asc LIMIT 3 OFFSET 0;
 */
fun pselect3() {
    peng.selectf { "name" regexp "i" or "sex" eq "m" orderBy "birth asc" limit 3 offset 0 }
        .forEach(::println)
}

/**
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

Insert:

```kotlin
/**
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

Modify:

```kotlin
/**
 * UPDATE .pet SET id = 3, name = 'Gray' WHERE ID=3;
 */
fun pupdate() {
    peng.update {
        id = 3
        name = "Gray"
    }
}
```

Delete:

```kotlin
/**
 * DELETE FROM .pet WHERE id = 3;
 */
fun pdelete() {
    peng.delete {
        id = 3
    }
}
```

## Contribution

For more example to check out [here][core].

[core]: https://github.com/ldcc/om/tree/master/example/src/event.
