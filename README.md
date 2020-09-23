# Simplex Data API
A world-specific data driven content registry api.  

**This is not finished. Expect to find missing features or bugs. Please report bugs to the issue tracker.** 

### How do I use it?
Use the `simplexdata` command. 
The Nbt config can also be found in your world's root folder, named `simplex-data-api-nbt.dat`

## Adding it as a dependency
Add the repository
```gradle
repositories {
  maven {
    url 'https://dl.bintray.com/simplex/simplex-maven/'
  }
}
```

Add the dependency
```gradle
dependencies {
   modImplementation 'io.github.simplex-dev:simplex-data-api:<the latest version>'
}
```

Find the latest version [here](https://bintray.com/simplex/simplex-maven/simplex-data-api)
