import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    static String ruta = "/home/dam/IdeaProjects/XML-Writer/products.xml";
    static ArrayList<Product> products;
    public static void main(String[] args) {
        FileReader fileReader = getFileReader();
        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = getXMLStreamReader(fileReader, factory);
        products = leerXML(reader);
        imprimirProductos(products);
    }

    public static FileReader getFileReader(){
        try{
            return new FileReader(ruta);
        }catch (FileNotFoundException e){
            System.out.println("No se encuentra el archivo: "+ruta +" "+ e.getMessage());
            return null;
        }
    }

    public static XMLStreamReader getXMLStreamReader(FileReader fileReader, XMLInputFactory factory){
        try{
            return factory.createXMLStreamReader(fileReader);
        }catch (Exception e){
            System.out.println("Error al crear el XMLStreamReader: "+e.getMessage());
            return null;
        }
    }

    public static ArrayList<Product> leerXML(XMLStreamReader reader) {
        ArrayList<Product> productos = new ArrayList<>();
        Product product = null;
        try {
            while (reader.hasNext()) {
                int tipo = reader.next();
                switch (tipo) {
                    case XMLStreamReader.START_ELEMENT:
                        if (reader.getLocalName().equals("producto")) {
                            product = new Product();
                        } else if (product != null) {
                            switch (reader.getLocalName()) {
                                case "codigo":
                                    product.setCodigo(reader.getElementText());
                                    break;
                                case "nombre":
                                    product.setDescripcion(reader.getElementText());
                                    break;
                                case "precio":
                                    product.setPrecio(Integer.parseInt(reader.getElementText()));
                                    break;
                            }
                        }
                        break;
                    case XMLStreamReader.END_ELEMENT:
                        if (reader.getLocalName().equals("producto") && product != null) {
                            productos.add(product);
                            product = null;
                        }
                        break;
                }
            }
            return productos;
        } catch (Exception e) {
            System.out.println("Error al leer el XML: " + e.getMessage());
            return null;
        }
    }

    public static void imprimirProductos(ArrayList<Product> productos){
        for(Product product: productos){
            System.out.println("codigo: " + product.getCodigo() + " nombre: " + product.getDescripcion() + " precio: " + product.getPrecio());
        }
    }
}
