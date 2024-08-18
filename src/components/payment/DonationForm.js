import React, { useState, useEffect } from 'react';
import { PayPalScriptProvider, PayPalButtons } from "@paypal/react-paypal-js";

function DonationForm({ projectId }) {

    return (
        <PayPalScriptProvider options={{ "client-id": "Ab-nahIRwRgjJTg5MiNHukBCoQ7oPK13uKGwo_0S_qX34YjkehK5SY53T4rVtZHslqguWYc7y8wFXkvQ" }}>
            <PayPalButtons
                createOrder={(data, actions) => {
                    return actions.order.create({
                        purchase_units: [{
                            amount: {
                                value: '1'
                            }
                        }]
                    });
                }}
                onApprove={async (data, actions) => {
                    
                    return actions.order.capture().then(async (details) => {
                        
                        const response = await fetch("http://localhost:8080/api/paypal/execute-payment", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify({ paymentId: data.orderID, payerId: data.payerID })
                        });
                        if (response.ok) {
                            alert("Donation Successful!");
                        } else {
                            alert("Donation Failed!");
                        }
                    });
                }}
            />
        </PayPalScriptProvider>
    );
}

export default DonationForm;
