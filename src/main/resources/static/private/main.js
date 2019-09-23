document.addEventListener('DOMContentLoaded', function() {
	var events;
	$.ajax({
		url : "/getEvents",
		type : "POST",
		data : JSON.stringify(event),
		dataType : 'json',
		contentType : 'application/json',
		success : function(dataResponse) {
			events = dataResponse;
			var calendarEl = document.getElementById('calendar');

			var calendar = new FullCalendar.Calendar(calendarEl, {
				locale : 'es',
				plugins : [ 'interaction', 'dayGrid', 'timeGrid' ],
				header : false,
				defaultDate : '2019-08-11',
				selectable : true,
				selectMirror : true,
				defaultView : 'timeGridWeek',
				columnHeaderFormat : {
					weekday : 'long'
				},
				allDaySlot : false,
				slotLabelInterval : {
					hours : 1
				},
				eventClick : function(info) {
					var eliminar=confirm("Desea eliminar?");
					if(eliminar){
						var event = {
								id: info.event.id,
								title : info.event.title,
								start : info.event.start,
								end : info.event.end,
							};
						$.ajax({
							url : "/eliminar",
							type : "POST",
							data : JSON.stringify(event),
							dataType : 'json',
							contentType : 'application/json',
							success : function(dataResponse) {
								calendar.getEventById(info.event.id).remove();
							}
						});
					
					}
				},
				// slotDuration : '00:15:00',
				minTime : "07:00:00",
				maxTime : "22:00:00",
				contentHeight : 'auto',

				select : function(arg) {
					var title = prompt('Event Title:');
					if (title) {
						var event = {
							title : title,
							start : arg.start,
							end : arg.end,
							allDay : arg.allDay
						};
						$.ajax({
							url : "/insertar",
							type : "POST",
							data : JSON.stringify(event),
							dataType : 'json',
							contentType : 'application/json',
							success : function(dataResponse) {
								calendar.addEvent(event);
							}
						});

					}
					calendar.unselect()
				},
				editable : true,
				eventLimit : true, // allow "more" link when too many events
				events : events

			});
			calendar.render();
		}
	})

});
