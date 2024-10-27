#!/bin/sh
java -jar telegramBot.jar <&- &
pid=$!
echo ${pid} > telegramBot.pid