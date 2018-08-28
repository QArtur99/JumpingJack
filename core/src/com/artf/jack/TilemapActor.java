package com.artf.jack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.AnimatedTiledMapTile;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Loads a Tiled map file (*.tmx), extends Actor to automatically render.
 */
public class TilemapActor extends Actor {
    // window dimensions
    public static int windowWidth = 800;
    public static int windowHeight = 640;

    private TiledMap tiledMap;
    private OrthographicCamera tiledCamera;
    private OrthoCachedTiledMapRenderer tiledMapRenderer;

    /**
     * Initialize Tilemap created with the Tiled Map Editor.
     */
    public TilemapActor(String filename, Stage theStage) {
        // set up tile map, renderer, and camera
        tiledMap = new TmxMapLoader().load(filename);

        int tileWidth = Integer.valueOf(tiledMap.getProperties().get("tilewidth").toString());
        int tileHeight = Integer.valueOf(tiledMap.getProperties().get("tileheight").toString());
        int numTilesHorizontal = Integer.valueOf(tiledMap.getProperties().get("width").toString());
        int numTilesVertical = Integer.valueOf(tiledMap.getProperties().get("height").toString());
        int mapWidth = tileWidth * numTilesHorizontal;
        int mapHeight = tileHeight * numTilesVertical;

        tiledMapRenderer = new OrthoCachedTiledMapRenderer(tiledMap);
        tiledMapRenderer.setBlending(true);
        tiledCamera = new OrthographicCamera(mapWidth, mapHeight);
        tiledCamera.setToOrtho(false, theStage.getCamera().viewportWidth, theStage.getCamera().viewportHeight);
        tiledCamera.update();

//        Array<AnimatedTiledMapTile> staticTiledMapTileArray = new Array<AnimatedTiledMapTile>();
//        Iterator<TiledMapTile> tiledMapTileInterator = tiledMap.getTileSets().getTileSet("water3").iterator();
//        while (tiledMapTileInterator.hasNext()) {
//            TiledMapTile tiledMapTile = tiledMapTileInterator.next();
//            if (tiledMapTile.getProperties().containsKey("name")) {
//                AnimatedTiledMapTile animatedTiledMapTile = (AnimatedTiledMapTile) tiledMapTile;
//                //animatedTiledMapTile.setAnimationIntervals(new int [] {10,10,10,10});
//                staticTiledMapTileArray.add((AnimatedTiledMapTile) animatedTiledMapTile);
//
//
//            }
//
//        }
//
//
//        //AnimatedTiledMapTile animatedTiledMapTile = new AnimatedTiledMapTile(1 / 3f, fame);
//        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get("Tile Layer 1");
//
//        int k = 0;
//        for (int i = 0; numTilesHorizontal > i; i++) {
//            for (int j = 0; numTilesVertical > j; j++) {
//                TiledMapTileLayer.Cell cell = layer.getCell(i, j);
//                if (cell != null && cell.getTile().getProperties().containsKey("name")) {
//                    k = k >= 5 ? 0 : k;
//                    cell.setTile(staticTiledMapTileArray.get(k));
//                    k++;
//                }
//            }
//            k = 0;
//        }



//        BaseActor space = new BaseActor(0,0, theStage);
//        space.loadTexture( "assets/map4.png" );
//        space.setSize(mapWidth,mapHeight);
//        BaseActor.setWorldBounds(space);

        // by adding object to Stage, can be drawn automatically
        theStage.addActor(this);

        // in theory, a solid boundary should be placed around the edge of the screen,
        //  but just in case, this map can be used to set boundaries
        BaseActor.setWorldBounds(mapWidth, mapHeight);
    }

    /**
     * Search the map layers for Rectangle Objects that contain a property (key) called "name" with associated value propertyName.
     * Typically used to store non-actor information such as SpawnPoint locations or dimensions of Solid objects.
     * Retrieve data as object, then cast to desired type: for example, float w = (float)obj.getProperties().get("width").
     */
    public ArrayList<MapObject> getRectangleList(String propertyName) {
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : layer.getObjects()) {
                if (!(obj instanceof RectangleMapObject))
                    continue;

                MapProperties props = obj.getProperties();

                if (props.containsKey("name") && props.get("name").equals(propertyName))
                    list.add(obj);
            }
        }
        return list;
    }


    public ArrayList<MapObject> getEnemyPlatformList(String propertyName) {
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : layer.getObjects()) {
                if (!(obj instanceof RectangleMapObject))
                    continue;

                MapProperties props = obj.getProperties();

                if (props.containsKey("enemyName") && props.get("enemyName").equals(propertyName))
                    list.add(obj);
            }
        }
        return list;
    }

    /**
     * Search the map layers for Tile Objects (tile-like elements of object layers)
     * that contain a property (key) called "name" with associated value propertyName.
     * Typically used to store actor information and will be used to create instances.
     */
    public ArrayList<MapObject> getTileList(String propertyName) {
        ArrayList<MapObject> list = new ArrayList<MapObject>();

        for (MapLayer layer : tiledMap.getLayers()) {
            for (MapObject obj : layer.getObjects()) {
                if (!(obj instanceof TiledMapTileMapObject))
                    continue;

                MapProperties props = obj.getProperties();

                // Default MapProperties are stored within associated Tile object
                // Instance-specific overrides are stored in MapObject

                TiledMapTileMapObject tmtmo = (TiledMapTileMapObject) obj;
                TiledMapTile t = tmtmo.getTile();
                MapProperties defaultProps = t.getProperties();

                if (defaultProps.containsKey("name") && defaultProps.get("name").equals(propertyName))
                    list.add(obj);

                // get list of default property keys
                Iterator<String> propertyKeys = defaultProps.getKeys();

                // iterate over keys; copy default values into props if needed
                while (propertyKeys.hasNext()) {
                    String key = propertyKeys.next();

                    // check if value already exists; if not, create property with default value
                    if (props.containsKey(key))
                        continue;
                    else {
                        Object value = defaultProps.get(key);
                        props.put(key, value);
                    }
                }
            }
        }
        return list;
    }

    public void act(float dt) {
        super.act(dt);
    }


    public void draw(Batch batch, float parentAlpha) {
        // adjust tilemap camera to stay in sync with main camera
        Camera mainCamera = getStage().getCamera();
        tiledCamera.position.x = mainCamera.position.x;
        tiledCamera.position.y = mainCamera.position.y;

        tiledCamera.update();
        tiledMapRenderer.setView(tiledCamera);


        // need the following code to force batch order,
        //  otherwise it is batched and rendered last
//        tiledMapRenderer.render(new int[] {0});
//        tiledMapRenderer.render(new int[] {1});
//        tiledMapRenderer.render(new int[] {2});
//        tiledMapRenderer.render(new int[] {3});
//        tiledMapRenderer.render(new int[] {4});
//        tiledMapRenderer.render(new int[] {5});
//        tiledMapRenderer.render(new int[] {6});
//        tiledMapRenderer.render(new int[] {7});


        //AnimatedTiledMapTile.updateAnimationBaseTime();
        Gdx.gl.glClearColor(
                Constants.BACKGROUND_COLOR.r,
                Constants.BACKGROUND_COLOR.g,
                Constants.BACKGROUND_COLOR.b,
                Constants.BACKGROUND_COLOR.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.end();
        AnimatedTiledMapTile.updateAnimationBaseTime();
        tiledMapRenderer.render();
        tiledMapRenderer.invalidateCache();
        batch.begin();

;


//        tiledMapRenderer.render(new int[] {8});
//        tiledMapRenderer.render(new int[] {9});
    }
}