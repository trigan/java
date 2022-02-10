package com.tintop.app.neo4j.connector;

import com.tintop.app.neptune.connector.NeptuneConnector;
import org.neo4j.driver.*;

public class Neo4JConnector {

    public Driver getConnection() {

        Driver neo4jDriver = GraphDatabase.driver("", AuthTokens.basic("", ""));
        return neo4jDriver;

    }

    public void selectRecords(String query, Driver driver){
        Session session = driver.session();
        Result result =  session.run("match (n) return distinct labels(n)");
        while(result.hasNext()){
            System.out.println(result.next().asMap());
        }
        //System.out.println("Result : " + result.toString());
    }

    public static void main(String[] args) {
        /*Neo4JConnector connector = new Neo4JConnector();
        Driver driver = connector.getConnection();
        connector.selectRecords("",driver);*/

        NeptuneConnector nepConnect = new NeptuneConnector();
        nepConnect.connectToNeptune();

    }
}
