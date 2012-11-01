package com.dnt.graph.biz.view.constants;

import org.neo4j.graphdb.RelationshipType;

public enum RelTypes implements RelationshipType
{
    KILL,
    USERS_REFERENCE,
    USER,
    NEO_NODE,
    KNOWS,
    CODED_BY,
    CUSTOMER_TO_ORDER,
    LINK
}
