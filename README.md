# Packaging challenge
This is an implementation of Mobiquity Packaging Challenge.
See docs/Packaging Challenge.pdf for definition of the challenge.

## Usage
#### Clone the project in local
```
git clone https://github.com/cobanokan/mobiquity-packer-library.git
```

#### Install library in local maven repository
```
mvn install
```

#### Run tests
```
mvn test
```

#### Generate coverage report with JaCoCo (report location target/site/jacoco/index.html)
```
mvn jacoco:report
```

####Include the library in maven project with following dependency
```
<dependency>
	<groupId>com.mobiquity</groupId>
	<artifactId>packer-implementation</artifactId>
	<version>1.0</version>
</dependency>
```

## Built With
Java 11
Maven
JUnit and Mockito for testing
JaCoCo for code coverage report
Log4j for logs

## License
This project is licensed under the MIT License - see the LICENSE.txt file for details