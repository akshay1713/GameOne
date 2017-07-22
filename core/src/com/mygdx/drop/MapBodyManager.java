package com.mygdx.drop;

import com.badlogic.gdx.maps.*;
import com.badlogic.gdx.maps.objects.*;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.Iterator;

/**
 * Taken from the tutorial - http://saltares.com/blog/games/populate-your-box2d-world-using-the-libgdx-maps-api/
 * Created by akshaysingh on 18/07/17.
 */
public class MapBodyManager {
    private float units;
    private World world;
    private ObjectMap<String, FixtureDef> materials = new ObjectMap<String, FixtureDef>();
    private Array<Body> bodies = new Array<Body>();


    public MapBodyManager(World world, float unitsPerPixel, String materialsFile){
        this.world = world;
        this.units = unitsPerPixel;
        if(materialsFile != null){
           System.out.println("Loading materials file");
        }
    }

    public void createPhysics(Map map, String layerName){
        MapLayer layer = map.getLayers().get(layerName);

        if (layer == null) {
            System.out.println("layer " + layerName + " does not exist");
            return;
        }
        System.out.println(layer.getName() + " " + layer.getProperties());
        MapObjects objects = layer.getObjects();
        Iterator<MapObject> objectIt = objects.iterator();
//        for (MapObject object:objects)
//        {
//            System.out.println(object.getName());
//        }

        while(objectIt.hasNext()) {
            MapObject object = objectIt.next();

            if (object instanceof TextureMapObject){
                continue;
            }

            Shape shape;
            BodyDef bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;

            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectangle = (RectangleMapObject)object;
                shape = getRectangle(rectangle);
            }
            else if (object instanceof PolygonMapObject) {
                shape = getPolygon((PolygonMapObject)object);
            }
            else if (object instanceof PolylineMapObject) {
                shape = getPolyline((PolylineMapObject)object);
            }
            else if (object instanceof CircleMapObject) {
                shape = getCircle((CircleMapObject)object);
            }
            else {
                System.out.println("non suported shape " + object);
                continue;
            }

            MapProperties properties = object.getProperties();
            String material = properties.get("material", "default", String.class);
            FixtureDef fixtureDef = new FixtureDef();

            if (fixtureDef == null) {
                System.out.println("material does not exist " + material + " using default");
                fixtureDef = materials.get("default");
            }

            fixtureDef.shape = shape;
//            fixtureDef.filter.categoryBits = Env.game.getCategoryBitsManager().getCategoryBits("level");

            Body body = world.createBody(bodyDef);
            body.createFixture(fixtureDef);

            bodies.add(body);

            fixtureDef.shape = null;
            shape.dispose();
        }
    }
    private Shape getRectangle(RectangleMapObject rectangleObject) {
        Rectangle rectangle = rectangleObject.getRectangle();
        PolygonShape polygon = new PolygonShape();
        Vector2 size = new Vector2((rectangle.x + rectangle.width * 0.5f) / units,
                (rectangle.y + rectangle.height * 0.5f ) / units);
        polygon.setAsBox(rectangle.width * 0.5f / units,
                rectangle.height * 0.5f / units,
                size,
                0.0f);
        return polygon;
    }

    private Shape getCircle(CircleMapObject circleObject) {
        Circle circle = circleObject.getCircle();
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(circle.radius / units);
        circleShape.setPosition(new Vector2(circle.x / units, circle.y / units));
        return circleShape;
    }

    private Shape getPolygon(PolygonMapObject polygonObject) {
        PolygonShape polygon = new PolygonShape();
        float[] vertices = polygonObject.getPolygon().getTransformedVertices();

        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; ++i) {
            worldVertices[i] = vertices[i] / units;
        }

        polygon.set(worldVertices);
        return polygon;
    }

    private Shape getPolyline(PolylineMapObject polylineObject) {
        float[] vertices = polylineObject.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length / 2];

        for (int i = 0; i < vertices.length / 2; ++i) {
            worldVertices[i] = new Vector2();
            worldVertices[i].x = vertices[i * 2] / units;
            worldVertices[i].y = vertices[i * 2 + 1] / units;
        }

        ChainShape chain = new ChainShape();
        chain.createChain(worldVertices);
        return chain;
    }

    public void destroyPhysics(){}
}
