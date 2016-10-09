package net.openhft.chronicle.engine.gui;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Tree;
import net.openhft.chronicle.engine.api.map.MapView;
import net.openhft.chronicle.engine.api.tree.Asset;
import net.openhft.chronicle.engine.api.tree.AssetTree;
import net.openhft.chronicle.engine.tree.TopologicalEvent;
import org.jetbrains.annotations.NotNull;

/**
 * @author Rob Austin.
 */
public class TreeControl {

    public static final String MAP_VIEW = "::map_view";
    public static final String QUEUE_VIEW = "::queue_view";

    final ItemClickEvent.ItemClickListener clickListener;

    public TreeControl(AssetTree assetTree, TreeUI treeUI) {

        final Tree tree = treeUI.tree;

        assetTree.registerSubscriber("",
                TopologicalEvent.class, e -> updateTree(assetTree, tree, e));

        clickListener = click -> {
            final String source = click.getItemId().toString();
            treeUI.contents.removeAllComponents();


            if (source.endsWith(MAP_VIEW)) {
                MapViewUI mapViewUI = new MapViewUI();
                treeUI.contents.addComponent(mapViewUI);

                final String path = source.substring(0, source.length() - MAP_VIEW.length());

                final MapView<Object, Object> mapView =
                        assetTree.acquireMap(path, Object.class, Object.class);

                MapControl MapControl = new MapControl(mapView, mapViewUI, path);
                MapControl.init();

            } else if (source.endsWith(MAP_VIEW)) {

                String path = source.substring(0, source.length() - MAP_VIEW.length());
                treeUI.contents.addComponent(new MapViewUI());
            }

        };

        tree.addItemClickListener(clickListener);
    }


    private void updateTree(@NotNull AssetTree assetTree, Tree tree, TopologicalEvent e) {
        if (e.assetName() == null || !e.added())
            return;

        tree.markAsDirty();

        Asset asset = assetTree.getAsset(e.fullName());
        assert asset != null;

        tree.addItem(e.fullName());
        tree.setItemCaption(e.fullName(), e.name());

        if (!"/".equals(e.assetName()))
            tree.setParent(e.fullName(), e.assetName());

        tree.setItemIcon(e.fullName(), new StreamResource(
                () -> TreeControl.class.getResourceAsStream("folder.png"), "folder"));

        tree.setChildrenAllowed(e.fullName(), true);

        if (asset.getView(MapView.class) != null) {
            tree.addItem(e.fullName() + MAP_VIEW);
            tree.setParent(e.fullName() + MAP_VIEW, e.fullName());
            tree.setItemCaption(e.fullName() + MAP_VIEW, "map");
            tree.setItemIcon(e.fullName() + MAP_VIEW, new StreamResource(
                    () -> TreeControl.class.getResourceAsStream("map.png"), "map"));
            tree.setChildrenAllowed(e.fullName() + MAP_VIEW, false);
        }

        if (asset.getView(MapView.class) != null) {
            tree.addItem(e.fullName() + QUEUE_VIEW);
            tree.setParent(e.fullName() + QUEUE_VIEW, e.fullName());
            tree.setItemCaption(e.fullName() + QUEUE_VIEW, "queue");
            tree.setItemIcon(e.fullName() + QUEUE_VIEW, new StreamResource(
                    () -> TreeControl.class.getResourceAsStream("map.png"), "map"));
            tree.setChildrenAllowed(e.fullName() + QUEUE_VIEW, false);
        }

    }


}