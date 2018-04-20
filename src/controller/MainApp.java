package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Producto;
import model.lista.Lista;
import model.venta.Dato;

public class MainApp extends Application {

    private Stage productoVista;
    private Stage ventaProductoVista;
    private Stage inicio;
    private BorderPane borde;
    Lista lista = new Lista();

    
    /**
     * The data as an observable list of Producto
     */
    private ObservableList<Producto> productoData = FXCollections.observableArrayList();
    
    
    /**
     * The data in sales as an observable list of datos
     */
    private ObservableList<Dato> ventaDatos = FXCollections.observableArrayList();
    
    /**
     * Constructor
     */
    public MainApp() {
    }

    /**
     * Returns the data as an observable list of Persons. 
     * @return
     */
    public ObservableList<Producto> getProductoData() {
        return productoData;
    }
    
    public ObservableList<Dato> getVentasDato(){
        return ventaDatos;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.inicio = primaryStage;
        this.inicio.setTitle("Inventario");
        //this.productoVista = primaryStage;
        //this.productoVista.setTitle("Inventario");

        // datos para testear 
        for(int i=0;i<20;i++){
            Producto prod = new Producto("Producto #"+i,i);
            productoData.add(prod);
        }
        Producto prod = new Producto("Coca cola 1 litro",100);
        Producto prod2 = new Producto("Coca cola 2 litros",200);
        Producto prod3 = new Producto("Coca cola 3 litro",400);
        Producto prod4 = new Producto("Coccaina",500);
        productoData.addAll(prod,prod2,prod3,prod4);
        
        initRootLayout();
        mostrarInicio();
        //showPersonOverview();
        
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Borde.fxml"));
            borde = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(borde);
            inicio.setScene(scene);
            inicio.show();
            //productoVista.setScene(scene);
            //productoVista.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarInicio(){
        try{
            // cargar el inicio
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/Inicio.fxml"));
            AnchorPane inicio = (AnchorPane) loader.load();
            
            // ponerle el borde al inicio
            borde.setCenter(inicio);
            
            InicioController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/view/ProductoVista.fxml"));
            AnchorPane productoVista = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            borde.setCenter(productoVista);
            
            // Give the controller access to the main app.
            ProductoVistaController controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the main stage.
     * @return
     */
    public Stage getProductoVista() {
        return productoVista;
    }
    
    public Stage getVentaProductoVista(){
        return ventaProductoVista;
    }
    
    /**
    * Opens a dialog to edit details for the specified person. If the user
    * clicks OK, the changes are saved into the provided person object and true
    * is returned.
    * 
    * @param producto the person object to be edited
    * @return true if the user clicked OK, false otherwise.
    */
   public boolean showEditarProductoDialog(Producto producto) {
       try {
           // Load the fxml file and create a new stage for the popup dialog.
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("/view/EditarProductoVista.fxml"));
           AnchorPane page = (AnchorPane) loader.load();

           // Create the dialog Stage.
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Editar producto");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(productoVista);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);

           // Set the person into the controller.
           EditarProductoController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setProducto(producto);

           // Show the dialog and wait until the user closes it
           dialogStage.showAndWait();

           return controller.isOkClicked();
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }
   
   public boolean showAgregarProductoDialog(Producto producto){
       try{
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("/view/AgregarProductoVista.fxml"));
           AnchorPane page = (AnchorPane) loader.load();
           
           // crear el nuevo dialogo
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Agregar producto");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(productoVista);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);
           
           // set the product into the controller
           AgregarProductoController controller = loader.getController();
           controller.setDialogStage(dialogStage);
           controller.setProducto(producto);
           
           dialogStage.showAndWait();
           
           return controller.isOkClicked();
       }catch(IOException e){
           e.printStackTrace();
           return false;
       }
   }
   
   public void mostrarVentasProducto(){
       try{
           // cargar la pantalla de venta
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("/view/VentaProductoVista.fxml"));
           AnchorPane ventasProducto = (AnchorPane) loader.load();
           
           // poner el scene de productos en el medio de root layout
           borde.setCenter(ventasProducto);
           
           // darle al controlador acceso al mainApp
           VentaProductoController controller = loader.getController();
           controller.setMainApp(this);
       }catch(IOException e){
           e.printStackTrace();
       }
   }
   
   public boolean mostrarAgregarVentaDialog(Dato dato){
       try{
           // carga la pantalla
           FXMLLoader loader = new FXMLLoader();
           loader.setLocation(MainApp.class.getResource("/view/AgregarVentaProductoVista.fxml"));
           AnchorPane page = (AnchorPane) loader.load();
           
           // crear el nuevo dialogo
           Stage dialogStage = new Stage();
           dialogStage.setTitle("Agregar productos de venta");
           dialogStage.initModality(Modality.WINDOW_MODAL);
           dialogStage.initOwner(ventaProductoVista);
           Scene scene = new Scene(page);
           dialogStage.setScene(scene);
           
           // poner el controlador
           AgregarProdVentaController controller = loader.getController();
           controller.setDialogStage(dialogStage, this);
           controller.setDato(dato);
           
           
           dialogStage.showAndWait();
           
           return controller.isOkClicked();
       }catch(IOException e){
           e.printStackTrace();
           return false;
       }
   }

    public static void main(String[] args) {
        launch(args);
    }
}   