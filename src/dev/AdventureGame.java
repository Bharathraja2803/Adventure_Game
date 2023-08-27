package dev;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdventureGame {
	private static final String Game_Data = """
			road,at the end of the road, W: hill, E:well house,S:valley,N:forest
			hill,on top of hill with a view in all directions,N:forest, E:road
			well house,inside a well house for a small spring,W:road,N:lake,S:stream
			valley,in a forest valley beside a tumbling stream,N:road,W:hill,E:stream
			forest,at the edge of a thick dark forest,S:road,E:lake
			lake,by an alpine lake surrounded by wildflowers,W:forest,S:well house
			stream,near a stream with a rocky bed,W:valley, N:well house
			""";
	
	private enum Compass{
		N,S,E,W;
		private static final String[] directions = {"North","South","East","West"};
		
		public String getString() {
			return directions[this.ordinal()];
		}
		
	}
	
	private record Location(String desc, Map<Compass, String> navigationMap){}
	
	private String lastPlace;
	
	private Map<String, Location> adventureMap = new HashMap<>();  
	
	public AdventureGame() {
		this(null);
	}
	
	public AdventureGame(String customLocations) {
		loadLocations(Game_Data);
		if(customLocations!=null) {
			loadLocations(customLocations);
		}
	}
	
	private void loadLocations(String data) {
		for(String s : data.split("\\R")) {
			String [] parts = s.split(",",3);
			Arrays.asList(parts).replaceAll(String::trim);
			Map<Compass, String> nextPlaces = loadDirections(parts[2]);
			Location location = new Location(parts[1],nextPlaces);
			adventureMap.put(parts[0], location);
		}
		//adventureMap.forEach((k,v) -> System.out.println("%s:%s%n".formatted(k,v)));
	}
	
	private Map<Compass, String> loadDirections(String nextPlaces){
		Map<Compass, String> directions = new HashMap<>();
		List<String> nextSteps = Arrays.asList(nextPlaces.split(","));
		nextSteps.replaceAll(String::trim);
		for(String nextPlace: nextSteps) {
			String[] splits = nextPlace.split(":");
			Compass compass = Compass.valueOf(splits[0].trim());
			String destinaton = splits[1].trim();
			directions.put(compass, destinaton);
		}
		
		return directions;
	}
	
	private void visit(Location location) {
		System.out.printf("""
				You're now standing in %s
					From here you can visit below locations:
				""",location.desc);
		location.navigationMap().forEach((k,v) -> System.out.printf("\t -> A %s is in the %s(%S)%n",v,k.getString(),k));
		System.out.println("Select you're compass (Q to quit: ");
	}
	
	public void move(String direction) {
		var nextPlaces = adventureMap.get(lastPlace).navigationMap;
		String nextPlace = null;
		if("NEWS".contains(direction)) {
			nextPlace = nextPlaces.get(Compass.valueOf(direction));
			if(nextPlace!=null) {
				play(nextPlace);
			}
		}else {
			System.out.println("Direction invalid try again!");
		}
	}
	
	public void play(String location) {
		if(adventureMap.containsKey(location)) {
			Location next = adventureMap.get(location);
			lastPlace = location;
			visit(next);
		}else {
			System.out.println(location +" is invalid location!");
		}
	}
}
