## Spring Shop
NOTE
[ERROR] Failed to execute goal [32morg.apache.maven.plugins:maven-compiler-plugin:3.1:testCompile[m [1m(default-testCompile)[m on project [36mshop[m: [1;31mFatal error compiling[m: java.lang.ExceptionInInitializerError: com.sun.tools.javac.code.TypeTags -> [1m[Help 1][m
CAUSE BY lombok.
HOW TO FIX: Add dependecy lombok for Java >= 10

    <dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<version>1.18.2</version>
			<scope>provided</scope>
		</dependency>




### Admin

- Dashboard: count all current users, categories, products, orders. Show latest orders.
- Customer Manager: list, delete, order history.
- Category Manager: list, add, edit, delete.
- Product Manager: list, add, edit, delete, upload image.
- Order Manager: list, show, delete. 

### Web

### API
RESTful API for Android.
