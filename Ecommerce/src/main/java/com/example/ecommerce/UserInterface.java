package com.example.ecommerce;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class UserInterface {
    GridPane loginpage;
    Button signInButton;
    Button placeOrder;
    Label welcomeLabel;
    HBox headerBar;
    HBox footerBar;
    VBox body;
    Customer loggedInCustomer;
    ObservableList<Product> itemsInCart = FXCollections.observableArrayList();


    ProductList productList = new ProductList();
    VBox productPage;

    Button placeOrderButton = new Button("Place Order");

    public BorderPane createContent(){
        BorderPane root = new BorderPane();
        root.setPrefSize(800,600);
       // root.getChildren().add(loginpage);
       // root.setCenter(loginpage);
        body = new VBox();
        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        root.setTop(headerBar);
        productPage = productList.getAllProducts();
        root.setCenter(body);
        body.getChildren().add(productPage);
        root.setBottom(footerBar);

        return root;
    }
    public UserInterface(){

        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void createLoginPage(){
        Text userNameText = new Text("Username");
        Text passwordText = new Text("Password");

        TextField userName = new TextField("amanchauhanru2000@gmail.com");
        userName.setPromptText("Type your username here");
        PasswordField password = new PasswordField();
        password.setText("Amansruchi");
        password.setPromptText("Type your password here");

        Button loginButoon = new Button("Login");
        Label messageLabel = new Label("Hi");

        loginpage = new GridPane();
       // loginpage.setStyle(" -fx-background-color:#87CEEB;");
        loginpage.setAlignment(Pos.CENTER);
        loginpage.setHgap(10);
        loginpage.setVgap(10);
        loginpage.add(userNameText,0,0);
        loginpage.add(userName,1,0);

        loginpage.add(passwordText,0,1);
        loginpage.add(password,1,1);
        loginpage.add(loginButoon,1,2);
        loginpage.add(messageLabel,0,2);

        loginButoon.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name = userName.getText();
                String pass = password.getText();
                login Login = new login();
                loggedInCustomer = Login.customerLogin(name,pass);
                if(loggedInCustomer!=null){
                    messageLabel.setText("Welcome :"+loggedInCustomer.getName() );
                    welcomeLabel.setText("Welcome "+loggedInCustomer.getName());
                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }else{
                    messageLabel.setText("Login Failed !");
                }
            }
        });

    }
    private void createHeaderBar(){
        Button homeButton = new Button();
        Image image = new Image("C:\\Users\\amanc\\IdeaProjects\\Ecommerce\\src\\main\\Untitled.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(100);
        homeButton.setGraphic(imageView);
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search Here");
        searchBar.setPrefWidth(180);

        Button searchButton = new Button("Search");

         signInButton = new Button("SignIn");
         welcomeLabel = new Label();

         Button cartButton = new Button("Cart");


        headerBar = new HBox();
        headerBar.setStyle("-fx-background-color:#D3D3D3");
        headerBar.setPadding(new Insets(8));
        headerBar.setAlignment(Pos.CENTER);
        headerBar.setSpacing(10);
        headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton);

        signInButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                body.getChildren().add(loginpage);
                headerBar.getChildren().remove(signInButton);
            }
        });
        cartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                body.getChildren().clear();
                VBox prodPage = productList.getProductInCart(itemsInCart);
                prodPage.setAlignment(Pos.CENTER);
                prodPage.setSpacing(10);
                prodPage.getChildren().add(placeOrderButton);
                body.getChildren().add(prodPage);
                footerBar.setVisible(false);
            }
        });

        placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(itemsInCart==null){
                    //please select a product first to place order
                    showDialog("Please add some product first in the cart to place order");
                    return;
                }
                if(loggedInCustomer==null){
                    showDialog("Please Login first to place Order");
                    return;
                }
                int count = Order.placeMultipleOrder(loggedInCustomer,itemsInCart);
                if(count != 0){
                    showDialog("Order for "+count+" Products placed successfully");
                }else{
                    showDialog("Order Failed !");
                }
            }
        });
    }
    private void createFooterBar(){


        Button buyNowButton = new Button("BuyNow");

        Button addToCart = new Button("Add to Cart");

        footerBar = new HBox();
        footerBar.setStyle("-fx-background-color:#D3D3D3");
        footerBar.setPadding(new Insets(8));
        footerBar.setAlignment(Pos.CENTER);
        footerBar.setSpacing(10);
        footerBar.getChildren().addAll(buyNowButton,addToCart);
        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               Product product = productList.getSelectedProduct();
               if(product==null){
                   //please select a product first to place order
                   showDialog("Please select a product first to place order");
                   return;
               }
                if(loggedInCustomer==null){
                    showDialog("Please Login first to place Order");
                    return;
                }
                boolean status = Order.placeOrder(loggedInCustomer,product);
                if(status== true){
                    showDialog("Order placed successfully");
                }else{
                    showDialog("Order Failed !");
                }
            }
        });
        addToCart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Product product = productList.getSelectedProduct();
                if(product==null){
                    //please select a product first to place order
                    showDialog("Please select a product first to add it to cart.");
                    return;
                }
                itemsInCart.add(product);
                showDialog("Selected Item has been added to Cart successfully.");
            }
        });
    }
    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setTitle("Message");
        alert.showAndWait();
    }
}
