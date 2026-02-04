import React from 'react';
import Navbar from './Navbar';
import HeroSection from './HeroSection';
import ServicesSection from './ServicesSection';
import Customer from '../HappyCustomer/Customer';
import NewAndNoteworthy from './NewAndNoteworthy';
import SmartSolutions from "../SmartSolutions/SmartSolutions";
import CustomerReview from "../CustomerReview/CustomerReview";

const Home = () => {
  return (
    <>
      <HeroSection />
      <ServicesSection />
      <NewAndNoteworthy />
      <SmartSolutions />
      <Customer />
      <CustomerReview />
    </>
  );
};

export default Home;
