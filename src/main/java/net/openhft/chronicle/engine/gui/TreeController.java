package net.openhft.chronicle.engine.gui;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Tree;
import net.openhft.chronicle.engine.api.column.ColumnView;
import net.openhft.chronicle.engine.api.column.MapColumnView;
import net.openhft.chronicle.engine.api.column.QueueColumnView;
import net.openhft.chronicle.engine.api.map.MapView;
import net.openhft.chronicle.engine.api.tree.Asset;
import net.openhft.chronicle.engine.api.tree.AssetTree;
import net.openhft.chronicle.engine.tree.ChronicleQueueView;
import net.openhft.chronicle.engine.tree.QueueView;
import net.openhft.chronicle.engine.tree.TopologicalEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Rob Austin.
 */
public class TreeController {

    public static final String MAP_VIEW = "::map_view";
    public static final String QUEUE_VIEW = "::queue_view";

    @NotNull
    final ItemClickEvent.ItemClickListener clickListener;

    public TreeController(@NotNull AssetTree assetTree, @NotNull TreeUI treeUI) {

        final Tree tree = treeUI.tree;

        assetTree.registerSubscriber("",
                TopologicalEvent.class, e -> updateTree(assetTree, tree, e));

        clickListener = click -> {
            final String source = click.getItemId().toString();
            treeUI.contents.removeAllComponents();


            if (source.endsWith(MAP_VIEW) || source.endsWith(QUEUE_VIEW)) {
                @NotNull MapViewUI mapViewUI = new MapViewUI();
                treeUI.contents.addComponent(mapViewUI);

                final int len = source.length();

                @NotNull final String path = source.endsWith(MAP_VIEW) ?
                        source.substring(0, len - MAP_VIEW.length()) :
                        source.substring(0, len - QUEUE_VIEW.length());

                @NotNull
                Asset asset = assetTree.acquireAsset(path);

                @Nullable
                final ColumnView view = source.endsWith(MAP_VIEW) ?
                        asset.acquireView(MapColumnView.class) :
                        asset.acquireView(QueueColumnView.class);

                @NotNull
                ColumnViewController mapControl = new ColumnViewController(view, mapViewUI, path);
                mapControl.init();

            }

        };

        tree.addItemClickListener(clickListener);
    }


    private void updateTree(@NotNull AssetTree assetTree, @NotNull Tree tree, @NotNull TopologicalEvent e) {
        if (e.assetName() == null || !e.added())
            return;

        @Nullable Asset asset = assetTree.getAsset(e.fullName());
        if (asset == null)
            return;

        tree.markAsDirty();

        tree.addItem(e.fullName());
        tree.setItemCaption(e.fullName(), e.name());

        if (!"/".equals(e.assetName()))
            tree.setParent(e.fullName(), e.assetName());

        tree.setItemIcon(e.fullName(), new StreamResource(
                () -> TreeController.class.getResourceAsStream("folder.png"), "folder"));

        tree.setChildrenAllowed(e.fullName(), true);

        MapView view = asset.getView(MapView.class);
        if (view == null || (!(view instanceof ChronicleQueueView))) {
            tree.addItem(e.fullName() + MAP_VIEW);
            tree.setParent(e.fullName() + MAP_VIEW, e.fullName());
            tree.setItemCaption(e.fullName() + MAP_VIEW, "map");
            tree.setItemIcon(e.fullName() + MAP_VIEW, new StreamResource(
                    () -> TreeController.class.getResourceAsStream("map.png"), "map"));
            tree.setChildrenAllowed(e.fullName() + MAP_VIEW, false);
        }

        if (asset.getView(QueueView.class) != null) {
            tree.addItem(e.fullName() + QUEUE_VIEW);
            tree.setParent(e.fullName() + QUEUE_VIEW, e.fullName());
            tree.setItemCaption(e.fullName() + QUEUE_VIEW, "queue");
            tree.setItemIcon(e.fullName() + QUEUE_VIEW, new StreamResource(
                    () -> TreeController.class.getResourceAsStream("map.png"), "map"));
            tree.setChildrenAllowed(e.fullName() + QUEUE_VIEW, false);
        }

    }


}
