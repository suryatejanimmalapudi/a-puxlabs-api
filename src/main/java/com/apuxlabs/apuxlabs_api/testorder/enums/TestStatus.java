package com.apuxlabs.apuxlabs_api.testorder.enums;

public enum TestStatus {
    PENDING_COLLECTION, // Waiting for Phlebotomist to draw blood
    COLLECTED,          // Blood drawn, sitting in Tech's pending queue
    REJECTED,           // Sample hemolyzed/bad, needs redraw
    COMPLETED           // Technician entered results
}