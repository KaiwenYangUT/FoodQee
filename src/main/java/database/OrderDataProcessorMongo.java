package database;

import com.mongodb.client.result.InsertOneResult;
import entities.Food;
import entities.Order;
import entities.OrderItem;
import interactors.DocumentOrderConverter;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderDataProcessorMongo implements OrderDataGateway {
    MongoCollectionFetcher mongoCollectionFetcher;
    public OrderDataProcessorMongo(MongoCollectionFetcher fetcher) {
        this.mongoCollectionFetcher = fetcher;
    }

    /**
     * @param order
     * @return
     */
    @Override
    public String save(Order order) {
        return null;
    }

    /**
     * @param order
     * @return
     */
    @Override
    public String delete(Order order) {
        return null;
    }

    /**
     * @param order
     * @return
     */
    @Override
    public ObjectId create(Order order) {
        Document newOrder = convertOrderToDocument(order);

        InsertOneResult result = this.mongoCollectionFetcher.getCollection("Orders").insertOne(newOrder);

        return result.getInsertedId().asObjectId().getValue();
    }

    /**
     * @return
     */
    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        mongoCollectionFetcher.getCollection("Orders")
                .find()
                .map(doc -> convertDocumentToOrder((Document) doc))
                .forEach(order -> orders.add((Order) order));

        return orders;
    }

    /**
     * @param userId
     * @return
     */
    @Override
    public List<Order> findAllByUser(ObjectId userId) {
        Bson queryFilter = Filters.eq("userID", userId);

        List<Order> orders = new ArrayList<>();

        mongoCollectionFetcher.getCollection("Orders")
                .find(queryFilter)
                .map(doc -> DocumentOrderConverter.convertDocumentToOrder((Document) doc))
                .forEach(order -> orders.add((Order) order));

        return orders;
    }

    /**
     * @param restaurantId
     * @return
     */
    @Override
    public List<Order> findAllByRestaurant(ObjectId restaurantId) {
        Bson queryFilter = Filters.eq("restaurantID", restaurantId);

        List<Order> orders = new ArrayList<>();

        mongoCollectionFetcher.getCollection("Orders")
                .find(queryFilter)
                .map(doc -> convertDocumentToOrder((Document) doc))
                .forEach(order -> orders.add((Order) order));

        return orders;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Order findById(ObjectId id) {
        Bson queryFilter = Filters.eq("_id", id);

        List<Order> orders = new ArrayList<>();

        mongoCollectionFetcher.getCollection("Orders")
                .find(queryFilter)
                .map(doc -> convertDocumentToOrder((Document) doc))
                .forEach(order -> orders.add((Order) order));

        if (orders.size() > 0)
            return orders.get(0);
        else
            return null;
    }

    public Order convertDocumentToOrder(Document document) {
        FoodDataGateway foodDataGateway = new FoodDataMongo(new MongoCollectionFetcher());
        List<OrderItem> items = document.getList("items", Document.class)
                .stream()
                .map(doc ->
                        new OrderItem(
                                doc.getObjectId("foodItemID"),
                                foodDataGateway.getFood(doc.getObjectId("foodItemID"), document.getObjectId("restaurantID")),
                                doc.getInteger("numberOfItem")))
                .collect(Collectors.toList());

        return new Order(document.getObjectId("_id"),  // ObjectId _id is auto-generated by DB for this order
                document.getDate("orderDate"),
                document.getObjectId("restaurantID"),
                document.getObjectId("userID"),
                items,
                document.getString("orderStatus"));
    }

    public Document convertOrderToDocument(Order order) {
        List<Document> items = order.getItems()
                .stream()
                .map(food -> new Document("numberOfItem", food.getNumberOfItem()).
                        append("food", convertFoodToDoc(food.getFood())).
                        append("foodItemID", food.getFoodItemID()))
                .collect(Collectors.toList());


        return new Document("restaurantID", order.getRestaurantID())
                .append("userID", order.getUserID())
                .append("items", items)
                .append("orderStatus", order.getOrderStatus())
                .append("orderDate", order.getOrderDate());
    }

    public Document convertFoodToDoc(Food curFood){
        return new Document("name", curFood.getName())
                .append("description", curFood.getDescription())
                .append("category", curFood.getCategory())
                .append("price", curFood.getPrice());
    }
}
