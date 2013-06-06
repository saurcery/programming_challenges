package start;
import java.util.ArrayList;
import java.util.HashMap;

/*
 * Calculate the detour distance between two different rides. Given four latitude 
 * longitude pairs, where driver one is traveling from point A to point B and driver
 * two is traveling from point C to point D, write a function (in your language of choice) 
 * to calculate the shorter of the detour distances the drivers would need to take to 
 * pick-up and drop-off the other driver.
 * 
 * 
 * Reference: http://www.movable-type.co.uk/scripts/latlong.html
 * 			: http://en.wikipedia.org/wiki/Haversine_formula 
 * */


public class detourDistance {

	static class point{
		private double longitude, latitude;

		public point(double latitude, double longitude){
			this.longitude = longitude;
			this.latitude = latitude;
		}

		void setLongitude(double longitude){
			this.longitude = longitude;
		}

		void setLatitude(double latitude){
			this.latitude = latitude;
		}

		double getLongitude(){
			return this.longitude;
		}

		double getLatitude(){
			return this.latitude;
		}
	}

	/* This list contains the different rides the drivers can take*/
	static ArrayList<String> possibleDetours = new ArrayList<String>();
	/* This Map is used to map the string(e.g. A, B, C) location to exact point on earth's surface i.e. the coordinates*/
	static HashMap<String, point> points = new HashMap<String, point>();
	/* Assuming any possible points on earth */
	static point A = new point(25, 75) , B = new point(35, 60), C = new point(25, 100), D = new point(35, 100);


	static double calcDistance(point source, point destination){

		final double radiusOfEarth = 3960; // miles

		double diffLat = Math.toRadians(destination.latitude - source.latitude);
		double diffLong = Math.toRadians(destination.longitude - source.longitude);

		double lat1 = Math.toRadians(source.latitude);
		double lat2 = Math.toRadians(destination.latitude);
		
		/* Haversine Formula */
		double a = Math.pow(Math.sin(diffLat/2) , 2) + Math.pow(Math.sin(diffLong/2), 2)* Math.cos(lat1)* Math.cos(lat2);
		double c  = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double distance = radiusOfEarth * c;

		return distance;
	}

	/* Method which calculates the total distance of the detour.
	 * Compares it with the other detour distance and returns 
	 * the index of the detour path taken*/
	static int shorterDetour(){
		/* list containing all the calculated detour distances */
		ArrayList<Double> dDistances = new ArrayList<Double>();
		double currentDistance = 0;

		for(String eachDetour : possibleDetours){
			char[] vertices = eachDetour.toCharArray();
			for(int index = 0; index < vertices.length-1; index ++){
				String source = ""+vertices[index];
				String destination = ""+vertices[index+1];
				currentDistance += calcDistance(points.get(source), points.get(destination));				
			}
			dDistances.add(currentDistance);
		}

		double minDD = dDistances.get(0);
		int index = 0;
		
		for(double current : dDistances)
			if(minDD > current){
				minDD = current;
				index = dDistances.indexOf(current);
			}
		System.out.println("Shorter detour distance is: "+ Math.round(minDD)+" miles");
		return index;
	}

	public static void main(String[] args){

		/* Populate the arraylist with two rides that the two drives can take*/
		possibleDetours.add("ACDB");
		possibleDetours.add("CABD");

		/* Initialize the hash map */
		points.put("A", A);
		points.put("B", B);
		points.put("C", C);
		points.put("D", D);

		/* Display the shorter detour path*/
		System.out.println("Shorter detour path is: "+possibleDetours.get(shorterDetour()));

	}
}