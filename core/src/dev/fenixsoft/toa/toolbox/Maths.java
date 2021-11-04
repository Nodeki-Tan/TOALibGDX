package dev.fenixsoft.toa.toolbox;


import com.badlogic.gdx.math.Vector2;

public class Maths {

    public static float Normaliced(float val, float max, float min) {
	    return (val - min) / (max - min);
	}

	public static float getPercentage(float current, float max) {
    	return (current * max) / 100;
    }

	public static float getRuleOf3(float value, float relativeTo, float fromTo) {
		return (value * fromTo) / relativeTo;
	}

	public static Vector2 mul(Vector2 a, Vector2 b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x * b.x;
		result.y = a.y * b.y;

		return result;
	}

	public static Vector2 mul(Vector2 a, float b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x * b;
		result.y = a.y * b;

		return result;
	}

	public static Vector2 add(Vector2 a, Vector2 b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x + b.x;
		result.y = a.y + b.y;

		return result;
	}

	public static Vector2 add(Vector2 a, int b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x + b;
		result.y = a.y + b;

		return result;
	}

	public static Vector2 add(Vector2 a, float b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x + b;
		result.y = a.y + b;

		return result;
	}

	public static Vector2 sub(Vector2 a, Vector2 b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x - b.x;
		result.y = a.y - b.y;

		return result;
	}

	public static Vector2 div(Vector2 a, Vector2 b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x / b.x;
		result.y = a.y / b.y;

		return result;
	}

	public static Vector2 div(Vector2 a, int b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x / b;
		result.y = a.y / b;

		return result;
	}

	public static Vector2 div(Vector2 a, float b){
		Vector2 result = new Vector2(0,0);

		result.x = a.x / b;
		result.y = a.y / b;

		return result;
	}

	public static Vector2 divInverse(Vector2 a, float b){
		Vector2 result = new Vector2(0,0);

		result.x = b / a.x;
		result.y = b / a.y;

		return result;
	}


}
