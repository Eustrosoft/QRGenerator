LIBS=../../../lib/core-3.4.0.jar:../../../lib/javax.servlet-api-4.0.0.jar:../../../lib/jfreesvg-3.4.3.jar
BASE_PATH=src/main/java/
PKG_PATH=org/eustrosoft/qr/
WEB_RESOURCES=web/WEB-INF/
PKG_CLASS_ALL=${PKG_PATH}
PKG_FILENAME=qrGenerator
WORK_PATH=work/
WORKDOC_PATH=${WORK_PATH}/javadoc
JAVAC?=javac
JAVA?=java
JAR?=jar

usage:
	@echo "Usage: make (all|build|clean)"
	@echo " where <target>:"
	@echo "  all - to do everything required"
	@echo "  build - to compile and package java classes"
	@echo "  clean - remove all constructed products"
	@echo "  usage - for this message"
all: build
build:
	@echo "-- buildng web application"
	mkdir -p ${WORK_PATH}
	cd ${BASE_PATH} \
	&& find org/ -type f -name "*.java" > sources && ${JAVAC} -cp ${LIBS} @sources \
	&& ${JAR} -c0f ${PKG_FILENAME}.jar ${PKG_CLASS_ALL} \
	&& mv ${PKG_FILENAME}.jar ../../../${WORK_PATH} && cp -R ../${WEB_RESOURCES} ../../../${WORK_PATH}
clean:
	@echo "-- cleaning all targets"
	rm -rf work/
	cd ${BASE_PATH} && find org/ -type f -name "*.class" | xargs rm && rm sources
