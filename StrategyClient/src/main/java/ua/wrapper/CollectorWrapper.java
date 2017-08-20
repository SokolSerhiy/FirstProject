package ua.wrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

public class CollectorWrapper  implements Collector{
	
	private static final String FIELD_NAME = "bellyful";
	
	private static final String COLLECT_FOOD_METHOD_NAME = "advancedCollectFood";

	private static final String COLLECT_WOOD_METHOD_NAME = "advancedCollectWood";
	
	private static final String COLLECT_STONE_METHOD_NAME = "advancedCollectStone";
	
	private static final String COLLECT_IRON_METHOD_NAME = "advancedCollectIron";
	
	public static final int HUNGRY = 30;

	public static final int ALMOST_DEAD = 5;

	private final Object human;

	private final Field bellyful;

	private final Method method;

	private final ResourceName colectedResource;

	public CollectorWrapper(Object human, ResourceName colectedResource) {
		this.human = human;
		this.colectedResource = colectedResource;
		Method method = findMethod(colectedResource, human.getClass());
		method.setAccessible(true);
		this.method = method;
		Field field = ReflectionUtils.findField(human.getClass(), FIELD_NAME);
		field.setAccessible(true);
		this.bellyful = field;
	}

	private Method findMethod(ResourceName colectedResource, Class<?> cl) {
		switch (colectedResource) {
		case FOOD: return ReflectionUtils.findMethod(cl, COLLECT_FOOD_METHOD_NAME);
		case WOOD: return ReflectionUtils.findMethod(cl, COLLECT_WOOD_METHOD_NAME);
		case STONE: return ReflectionUtils.findMethod(cl, COLLECT_STONE_METHOD_NAME);
		case IRON: return ReflectionUtils.findMethod(cl, COLLECT_IRON_METHOD_NAME);
		default: throw new IllegalArgumentException("Не може бути");
		}
	}
	
	public ResourceName getCollectedResourceName(){
		return colectedResource;
	}

	@Override
	public boolean isHungry() {
		return getBellyful()<=ALMOST_DEAD;
	}

	@Override
	public void takeFood(int food) {
		setBellyful(getBellyful() + food);
	}

	@Override
	public void setBellyful(int food) {
		ReflectionUtils.setField(bellyful, human, food);
	}
	
	@Override
	public int getBellyful(){
		return (int) ReflectionUtils.getField(bellyful, human);
	}
	
	@Override
	public int collect() {
		return (int) ReflectionUtils.invokeMethod(method, human);
	}

}
