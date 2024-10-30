WORK_PATH=work/

LIBS=../../../lib/core-3.4.0.jar:../../../lib/javax.servlet-api-4.0.0.jar:../../../lib/jfreesvg-3.4.3.jar
BOT_LIBS=../../../../lib/java-telegram-bot-api-7.9.1.jar
BASE_PATH=src/main/java/
BOTS_DIR=Bots
WEB_RESOURCES=web/WEB-INF/

PKG_FILENAME=qrGenerator.jar
PKG_BOTS_FILENAME=telegramBot.jar
PKG_BOTS_MANIFEST=../resources/META-INF/MANIFEST.MF
PKG_BOTS_RESOURCES=../resources/application.properties

PKG_PATH=org/eustrosoft/qr/
PKG_BOTS_PATH=org/eustrosoft/bot/

PKG_CLASSES=${PKG_PATH}
PKG_BOTS_CLASSES=${PKG_BOTS_PATH}

JAVAC?=javac
JAR?=jar

usage:
	@echo "Usage: make (all|build|clean)"
	@echo " where <target>:"
	@echo "  all - to do everything required"
	@echo "  build - to compile and package java classes"
	@echo "  clean - remove all constructed products"
	@echo "  usage - for this message"
all: clean build
build:
	@echo "-- buildng web application"
	mkdir -p ${WORK_PATH}
	cd ${BASE_PATH} \
	&& find org/ -type f -name "*.java" > sources && ${JAVAC} -cp ${LIBS} @sources \
	&& ${JAR} -c0f ${PKG_FILENAME} ${PKG_CLASSES} \
	&& mv ${PKG_FILENAME} ../../../${WORK_PATH} && cp -R ../${WEB_RESOURCES} ../../../${WORK_PATH}
	@echo "-- buildng bot application"
	cd ${BOTS_DIR}/${BASE_PATH} \
	&& find org/ -type f -name "*.java" > sources && ${JAVAC} -cp ${BOT_LIBS} @sources \
	&& ${JAR} -cmf ${PKG_BOTS_MANIFEST} ${PKG_BOTS_FILENAME} ${PKG_BOTS_CLASSES} ${PKG_BOTS_RESOURCES} \
	&& mv ${PKG_BOTS_FILENAME} ../../../../${WORK_PATH}
	cp ${BOTS_DIR}/dev/scripts/* ${WORK_PATH}
clean:
	@echo "-- cleaning all targets"
	rm -rf work/
	cd ${BASE_PATH} && find org/ -type f -name "*.class" | xargs --no-run-if-empty rm && rm -f sources && rm -f ${PKG_FILENAME}
	cd ${BOTS_DIR}/${BASE_PATH} && find org/ -type f -name "*.class" | xargs --no-run-if-empty rm && rm -f sources && rm -f ${PKG_BOTS_FILENAME}
