package com.clipsource.mongodb;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;


public class MongodbApplication {

	public static void main(String[] args) {

		ConnectionString connectionString = new ConnectionString("mongodb+srv://amc-dev-delivery-order:477AtrqpLcLsMNzv@cluster0.wk1nse8.mongodb.net/?retryWrites=true&w=majority");
		MongoClientSettings settings = MongoClientSettings.builder()
				.applyConnectionString(connectionString)
				.serverApi(ServerApi.builder()
						.version(ServerApiVersion.V1)
						.build())
				.build();


		try (MongoClient mongoClient = MongoClients.create(settings)) {
			MongoDatabase mongoDatabase = mongoClient.getDatabase("amc-delivery-system");
			MongoCollection<Document> dataTransformationCollection = mongoDatabase.getCollection("data-transformation");

			// find one document with new Document
			Document deliveryData = dataTransformationCollection.find(new Document("key", "63e7c6371f795")).first();
			System.out.println("DeliveryData 1: " + deliveryData.toJson());

			// findOneAndUpdate
			Bson filter = eq("key", "63e7c6371f795");
			Bson update1 = set("delivered", true);
			Bson update2 = set("deliveredAt", "2023-02-17 15:12:00");
			Bson update3 = set("message", "A test message for umar");
			Bson updates = combine(update1, update2, update3);

			// returns the old version of the document before the update.
			Document oldVersion = dataTransformationCollection.findOneAndUpdate(filter, updates);
			System.out.println("\n=> FindOneAndUpdate operation. Printing the old version by default:");

		}

	}

}
