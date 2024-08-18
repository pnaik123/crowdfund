import React from 'react';
import { useLocation } from 'react-router-dom';

const PaymentRedirect = () => {
  const { search } = useLocation();
  const queryParams = new URLSearchParams(search);

  const razorpayPaymentId = queryParams.get('razorpay_payment_id');
  const razorpayPaymentLinkId = queryParams.get('razorpay_payment_link_id');
  const razorpayPaymentLinkReferenceId = queryParams.get('razorpay_payment_link_reference_id');
  const razorpayPaymentLinkStatus = queryParams.get('razorpay_payment_link_status');
  const razorpaySignature = queryParams.get('razorpay_signature');

  return (
    <div>
      <h1>Payment Details</h1>
      <p>Razorpay Payment ID: {razorpayPaymentId}</p>
      <p>Razorpay Payment Link ID: {razorpayPaymentLinkId}</p>
      <p>Razorpay Payment Link Reference ID: {razorpayPaymentLinkReferenceId}</p>
      <p>Razorpay Payment Link Status: {razorpayPaymentLinkStatus}</p>
      <p>Razorpay Signature: {razorpaySignature}</p>
    </div>
  );
};

export default PaymentRedirect;
