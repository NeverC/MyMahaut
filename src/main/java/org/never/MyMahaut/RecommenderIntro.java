package org.never.MyMahaut;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

public class RecommenderIntro {
	public static void main(String[] args) {
//		try {
//			DataModel model = new FileDataModel(new File("intro.csv"));
//			UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, similarity, model);
//			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
//
//			List<RecommendedItem> recommendations = recommender.recommend(2, 1);
//			for (RecommendedItem recommendation : recommendations) {
//			  System.out.println(recommendation);
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (TasteException e) {
//			e.printStackTrace();
//		}
		
		RandomUtils.useTestSeed();
		DataModel model = null;
		try {
			model = new FileDataModel (new File("intro.csv"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		RecommenderEvaluator evaluator	 = new AverageAbsoluteDifferenceRecommenderEvaluator ();
		
		RecommenderBuilder builder = new RecommenderBuilder() {
			public Recommender buildRecommender(DataModel model)throws TasteException {
				UserSimilarity similarity = new PearsonCorrelationSimilarity (model);
				UserNeighborhood neighborhood = new NearestNUserNeighborhood (2, similarity, model);
				return new GenericUserBasedRecommender (model, neighborhood, similarity);
			}
		};
		double score = 0;
		try {
			score = evaluator.evaluate(
			builder, null, model, 0.7, 1.0);
		} catch (TasteException e) {
			e.printStackTrace();
		}
		System.out.println(score);	

	}
}

