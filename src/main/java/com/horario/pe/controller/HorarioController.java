package com.horario.pe.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.horario.pe.model.Event;

@Controller
@RequestMapping({ "/", "" })
public class HorarioController {
	private ObjectMapper jsonMapper;
	private File file = new File("src/main/java/event.json");

	@GetMapping()
	public String renderizarIndex(Model model) throws IOException {
		model.addAttribute("events", getEventsFromFile());

		return "views/pages/horario";
	}

	@PostMapping("/getEvents")
	@ResponseBody
	public Event[] getEvents() throws IOException {
		return getEventsFromFile();
	}

	@PostMapping("insertar")
	@ResponseBody
	public Byte insertar(@RequestBody Event event) throws IOException {
		Event[] events = getEventsFromFile();
		List<Event> listEvents = new ArrayList<Event>(Arrays.asList(events));
		if (listEvents.size() != 0) {
			event.setId(events[events.length - 1].getId() + 1);
		} else {
			event.setId(1);
		}
		listEvents.add(event);
		jsonMapper.writeValue(file, listEvents);
		return Byte.parseByte("1");
	}

	@PostMapping("modificar")
	@ResponseBody
	public Byte modificar(@RequestBody Event event) throws IOException {
		Event[] events = getEventsFromFile();
		for (Event e : events) {
			if (e.getId() == event.getId()) {
				e = event;
			}
		}
		jsonMapper.writeValue(file, events);
		return Byte.parseByte("1");
	}

	@PostMapping("eliminar")
	@ResponseBody
	public Byte eliminar(@RequestBody Event event) throws IOException {
		if (event == null) {
			System.out.println("Es nulo papi");
		}
		Event[] events = getEventsFromFile();
		List<Event> listEvents = new ArrayList<>(Arrays.asList(events));
		for (int i = 0; i < listEvents.size(); i++) {
			if (event.getId() == listEvents.get(i).getId()) {
				listEvents.remove(i);
				break;
			}
		}
		for (Event e : listEvents) {
			System.out.println(e);
		}
		System.out.println(event.getId());
		events = listEvents.toArray(events);
		jsonMapper.writeValue(file, listEvents);
		return Byte.parseByte("1");
	}

	public Event[] getEventsFromFile() throws IOException {

		jsonMapper = new ObjectMapper();
		Event[] events = jsonMapper.readValue(file, Event[].class);
		return events;
	}
}
