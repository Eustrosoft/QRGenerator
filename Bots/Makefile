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
	mvn clean package
clean:
	@echo "-- cleaning all targets"
	mvn clean