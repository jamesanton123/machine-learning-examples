package tutorials.practice;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.LoglikelihoodSimilarity;

/**
 * A tutorial I was following on
 * https://www.youtube.com/watch?v=yD40rVKUwPI
 *
 */
public class HelloMachineWorld {
	private static final String DATA_FILE_NAME = "u.data";
	
	public static void main(String[] args) throws IOException, URISyntaxException, TasteException{
		new HelloMachineWorld().getRecommendations();		
	}
	
	public void getRecommendations() throws IOException, URISyntaxException, TasteException{
		URL url = HelloMachineWorld.class.getClassLoader().getResource(DATA_FILE_NAME);			
		DataModel dm = new FileDataModel(new File(url.toURI()));
		ItemSimilarity similarity =  new LogLikelihoodSimilarity(dm);
		
		GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, similarity);
		for(LongPrimitiveIterator items = dm.getItemIDs(); items.hasNext();){
			long itemId = items.nextLong();
			List<RecommendedItem> recommendations = recommender.mostSimilarItems(itemId, 1);
			System.out.println("Item id = " + itemId + " recommendation = " + recommendations.get(0).getItemID());
		}
		
	}
	
	
}
