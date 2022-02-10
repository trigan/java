package com.tintop.app.neptune.connector;

import org.apache.tinkerpop.gremlin.driver.Cluster;
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.T;

import static org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal;

public class NeptuneConnector {

    public void connectToNeptune(){

        Cluster.Builder builder = Cluster.build();
        builder.addContactPoint("localhost");
        builder.port(8182);
        builder.enableSsl(true);
        builder.keyCertChainFile("SFSRootCAG2.pem");

        Cluster cluster = builder.create();
        System.out.println("Cluster : " + cluster);

        GraphTraversalSource g = traversal().withRemote(DriverRemoteConnection.using(cluster));

        System.out.println("GTS : " + g);

        g.addV("Person").property("Name","Justin").iterate();

        // Add a vertex with a user-supplied ID.
        g.addV("Custom Label").property(T.id, "CustomId1").property("name", "Custom id vertex 1").iterate();
        g.addV("Custom Label").property(T.id, "CustomId2").property("name", "Custom id vertex 2").iterate();

        GraphTraversal t = g.V().limit(3).elementMap();

        t.forEachRemaining(
                e->System.out.println(t.toList())
        );

        cluster.close();

        //https://docs.aws.amazon.com/neptune/latest/userguide/access-graph-gremlin-java.html
        //https://medium.com/@ravelantunes/connecting-to-aws-neptune-from-local-environment-64c836548e89
        //ssh -i "C:\Users\shibu\Documents\AWS\jump_box_default.pem" ec2-user@18.117.177.242 -N -L 8182:172.31.24.188:8182

    }
}
