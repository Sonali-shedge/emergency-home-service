import React from 'react'
import Navbar from './Navbar'
import HeroSection from './HeroSection'
import ServicesSection from './ServicesSection'
import Customer from '../HappyCustomer/Customer'
import NewAndNoteworthy from './NewAndNoteworthy'

const Home = () => {
  return (
   <>

   <HeroSection/>
   <ServicesSection/>
   <NewAndNoteworthy/>
   <Customer/>
   
   </>
  )
}

export default Home


