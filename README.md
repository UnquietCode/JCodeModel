#JCodeModel
JCodeModel is a fork of the existing Sun codemodel, part of the GlassFish project.
With java.net in ruins, it seemed liked a good time to fork this project and
clean it up as well as add some new features (for example, Java 7 syntax).

The current release is `1.0`, and contains several fixes, cosmetic improvements to
the output, and some improved functionality for inner classes to support the
development of [Flapi](https://github.com/UnquietCode/Flapi).

Many thanks to the original contributors, namely Kohsuke.

#Using
Using Maven-style dependencies & repositories, add this information:
```
<repositories>
  <repository>
    <id>uqc</id>
    <name>UnquietCode Repository</name>
    <url>http://www.unquietcode.com/maven/releases</url>
  </repository>
</repositories>

...

<dependency>
  <groupId>unquietcode.tools.jcodemodel</groupId>
  <artifactId>codemodel</artifactId>
  <version>1.0</version>
</dependency>
```
