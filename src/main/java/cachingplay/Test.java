package cachingplay;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class Test {
	
	static Cache<String, List<Double>> cache = CacheBuilder.newBuilder()
			.expireAfterWrite(1, TimeUnit.MINUTES)
			.build();

	public static void main(String[] args) {
		
		
		Callable<List<Double>> callable = new Callable<List<Double>>() {
			
			public List<Double> call() throws Exception {
				List<Double> list = new ArrayList<Double>();
				for (int i = 0; i < 1000; i++) {
					list.add(Math.random());
				}
				return list;
			}
			
		};
		
		try {
			List<Double> list = cache.get("key", callable);
			while (true) {
				if (list != cache.get("key", callable)) {
					System.out.println("not same");
					break;
				}
			}
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}

}
