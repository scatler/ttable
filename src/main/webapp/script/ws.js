var services = angular.module('services', []);
services.factory('timeTableWSService',
    function() {
        var service = {};
        service.connect = function() {
            if (service.ws) {
                return;
            }
            //var ws = new WebSocket("ws://" + window.location.host + "/timetable");
            var ws = new WebSocket("ws://localhost:9280/javaee7-angular/timetable");


            //TODO: see commented code
            ws.onopen = function() {
/*                ws.send(JSON.stringify({
                    type: "auth",
                    token: $cookies.token
                }));*/
                console.log("Succeeded to open a connection");
            };

            ws.onerror = function() {
                console.log("Failed to open a connection");
            };
            ws.onmessage = function(message) {
                var obj = JSON.parse(message.data);
                if (obj.result) return;

                if (obj.id) {
                    setTimeout(function() {
                        service.callback(obj);
                    }, 1000)
                }
            };
            service.ws = ws;
        };
        service.subscribe = function(callback) {
            service.callback = callback;
        };
        return service;
    }
);