package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;


public class Q2TestV2 {

	String cannotBuyPath = "exceptions.CannotBuyException";
	String noEnoughMoneyPath = "exceptions.NoEnoughMoneyException";
	String noEnoughSpacePath = "exceptions.NoEnoughSpaceException";
	
	String buyablePath = "interfaces.Buyable";
	
	String artifactPath = "artifacts.Artifact";
	String paintingPath = "artifacts.Painting";
	String statuePath = "artifacts.Statue";
	String colorTypePath = "artifacts.ColorType";
	String materialType = "artifacts.MaterialType";
	
	String museumPath = "places.Museum";
	
	// ////////////////helper methods////////////////////
		private void testClassIsAbstract(Class aClass) {
			assertTrue(aClass.getSimpleName() + " should be an abtract class.",
					Modifier.isAbstract(aClass.getModifiers()));
		}

		private void testClassImplementsInterface(Class aClass, Class iClass) {
			assertTrue(
					aClass.getSimpleName() + " should implement "
							+ iClass.getSimpleName(),
					iClass.isAssignableFrom(aClass));
		}

		private void testIsInterface(Class iClass) {
			assertTrue(iClass.getSimpleName() + " should be interface",
					iClass.isInterface());
		};

		private void testConstructorExists(Class aClass, Class[] inputs) {
			boolean thrown = false;
			try {
				aClass.getConstructor(inputs);
			} catch (NoSuchMethodException e) {
				thrown = true;
			}

			if (inputs.length > 0) {
				String msg = "";
				int i = 0;
				do {
					msg += inputs[i].getSimpleName() + " and ";
					i++;
				} while (i < inputs.length);

				msg = msg.substring(0, msg.length() - 4);

				assertFalse(
						"Missing constructor with " + msg + " parameter"
								+ (inputs.length > 1 ? "s" : "") + " in "
								+ aClass.getSimpleName() + " class.",

						thrown);
			} else
				assertFalse(
						"Missing constructor with zero parameters in "
								+ aClass.getSimpleName() + " class.",

						thrown);

		}

		private void testConstructorInitialization(Object createdObject,
				String[] names, Object[] values) throws NoSuchMethodException,
				SecurityException, IllegalArgumentException, IllegalAccessException {

			for (int i = 0; i < names.length; i++) {

				Field f = null;
				Class curr = createdObject.getClass();
				String currName = names[i];
				Object currValue = values[i];

				while (f == null) {

					if (curr == Object.class)
						fail("Class " + createdObject.getClass().getSimpleName()
								+ " should have the instance variable \""
								+ currName + "\".");
					try {
						f = curr.getDeclaredField(currName);
					} catch (NoSuchFieldException e) {
						curr = curr.getSuperclass();
					}
				}
				f.setAccessible(true);

				assertEquals("The constructor of the "
						+ createdObject.getClass().getSimpleName()
						+ " class should initialize the instance variable \""
						+ currName + "\" correctly.", currValue,
						f.get(createdObject));

			}

		}

		@SuppressWarnings("rawtypes")
		private void testInstanceVariablesArePresent(Class aClass, String varName,
				boolean implementedVar) throws SecurityException {

			boolean thrown = false;
			try {
				aClass.getDeclaredField(varName);
			} catch (NoSuchFieldException e) {
				thrown = true;
			}
			if (implementedVar) {
				assertFalse("There should be " + varName
						+ " instance variable in class " + aClass.getName(), thrown);
			} else {
				assertTrue("There should not be " + varName
						+ " instance variable in class " + aClass.getName()
						+ ", it should be inherited from the super class", thrown);
			}
		}

		@SuppressWarnings("rawtypes")
		private void testInstanceVariablesArePrivate(Class aClass, String varName)
				throws NoSuchFieldException, SecurityException {
			Field f = aClass.getDeclaredField(varName);
			assertEquals(
					varName + " instance variable in class " + aClass.getName()
							+ " should not be accessed outside that class", 2,
					f.getModifiers());
		}

		@SuppressWarnings("rawtypes")
		private void testInstanceVariableIsPrivate(Class aClass, String varName)
				throws NoSuchFieldException, SecurityException {
			Field f = aClass.getDeclaredField(varName);
			assertEquals(
					varName + " instance variable in class " + aClass.getName()
							+ " should not be accessed outside that class", 2,
					f.getModifiers());
		}

		private static boolean containsMethodName(Method[] methods, String name) {
			for (Method method : methods) {
				if (method.getName().equals(name))
					return true;
			}
			return false;
		}

		private void testMethodAbsence(Class aClass, String methodName) {
			Method[] methods = aClass.getDeclaredMethods();
			assertFalse(aClass.getSimpleName() + " class should not override "
					+ methodName + " method",
					containsMethodName(methods, methodName));
		}

		private void testInterfaceMethod(Class iClass, String methodName,
				Class returnType, Class[] parameters) {
			Method[] methods = iClass.getDeclaredMethods();
			assertTrue(iClass.getSimpleName() + " interface should have "
					+ methodName + "method",
					containsMethodName(methods, methodName));

			Method m = null;
			boolean thrown = false;
			try {
				m = iClass.getDeclaredMethod(methodName, parameters);
			} catch (NoSuchMethodException e) {
				thrown = true;
			}

			assertTrue(
					"Method " + methodName
							+ " should have the following set of parameters : "
							+ Arrays.toString(parameters), !thrown);
			assertTrue("wrong return type", m.getReturnType().equals(returnType));

		}

		///////////////// testing the interface \\\\\\\\\\\\\\\
		@Test(timeout = 1000)
		public void testBuyableInterfaceExisit() throws ClassNotFoundException{
			testIsInterface(Class.forName(buyablePath));
		}
		
		@Test(timeout = 1000)
		public void testisBuyableMethodInDraftable() {
			try {
				testInterfaceMethod(Class.forName(buyablePath), "isBuyable",boolean.class, null);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statement."
						+ e.getClass() + " occurred");
			}
		}
		
		@Test(timeout = 1000)
		public void testgetPriceMethodInDraftable() {
			try {
				testInterfaceMethod(Class.forName(buyablePath), "getPrice",int.class, null);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				fail("Please check the console for the error, its an error from this catch statement."
						+ e.getClass() + " occurred");
			}
		}
		///////////////////////////////////////////////////////
		
		
		
		
		
		///////////////// testing the Exceptions \\\\\\\\\\\\\\\
		@Test(timeout = 1000)
		public void testConstructorCannotBuyExceptionEmpty()
				throws ClassNotFoundException {
			Class[] inputs = {};
			testConstructorExists(Class.forName(cannotBuyPath), inputs);
		}

		@Test(timeout = 1000)
		public void testConstructorCannotBuyExceptionWithString()
				throws ClassNotFoundException {
			Class[] inputs = { String.class };
			testConstructorExists(Class.forName(cannotBuyPath), inputs);
		}
		
		@Test(timeout = 1000)
		public void testConstructorNoEnoughMoneyExceptionEmpty()
				throws ClassNotFoundException {
			Class[] inputs = {};
			testConstructorExists(Class.forName(noEnoughMoneyPath), inputs);
		}
		
		@Test(timeout = 1000)
		public void testConstructorNoEnoughMoneyExceptionWithString()
				throws ClassNotFoundException {
			Class[] inputs = { String.class };
			testConstructorExists(Class.forName(noEnoughMoneyPath), inputs);
		}
				
		@Test(timeout = 1000)
		public void testConstructorNoEnoughSpaceExceptionEmpty()
				throws ClassNotFoundException {
					Class[] inputs = {};
					testConstructorExists(Class.forName(noEnoughSpacePath), inputs);
				}
				
		@Test(timeout = 1000)
		public void testConstructorNoEnoughSpaceExceptionWithString()
				throws ClassNotFoundException {
					Class[] inputs = { String.class };
					testConstructorExists(Class.forName(noEnoughSpacePath), inputs);
				}
		////////////////////////////////////////////////////////	
		
		
		
		
		
		///////////////// testing the Artifact class \\\\\\\\\\\\\\\
		@Test(timeout = 1000)
		public void testArtifactClassIsAbstract() throws ClassNotFoundException {
			testClassIsAbstract(Class.forName(artifactPath));
		}
		
		@Test(timeout = 1000)
		public void testArtifactClassImplementsBuyableInterface() {
			try {
				testClassImplementsInterface(Class.forName(artifactPath),Class.forName(buyablePath));
			} catch (ClassNotFoundException e) {
				assertTrue(e.getClass().getName() + " occurred: " + e.getMessage(),	false);
			}
		}
		////////////////////////////////////////////////////////////	
		
	
		
		
		
		///////////////// testing the painting class \\\\\\\\\\\\\\\
	
		
		@Test(timeout = 1000)
		public void testisBuyableMethodInPaintingClassLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			Class aClass = Class.forName(paintingPath);
			
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colorTypePath), "ACRYLIC");

			Constructor<?> c = Class.forName(paintingPath)
					.getConstructor(String.class, int.class ,String.class,double.class,double.class,Class.forName(colorTypePath));
			Object painting = c.newInstance("x1",1900,"A1",1.0,2.0,color);
			
			
			Method m = painting.getClass().getDeclaredMethod("isBuyable");
			assertEquals(
					"paintings with ACRYLIC colorType are buyable",
					true, m.invoke(painting));
			
	
		} 
		
		@Test(timeout = 1000)
		public void testisBuyableMethodInPaintingClassLogicFaill() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			Class aClass = Class.forName(paintingPath);
			
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colorTypePath), "CHARCOAL");

			Constructor<?> c = Class.forName(paintingPath)
					.getConstructor(String.class, int.class ,String.class,double.class,double.class,Class.forName(colorTypePath));
			Object painting = c.newInstance("x1",1900,"A1",1.0,2.0,color);
			
			
			Method m = painting.getClass().getDeclaredMethod("isBuyable");
			assertEquals(
					"Only paintings with ACRYLIC colorType are buyable , Otherwise should return false",
					false, m.invoke(painting));
			
	
		}
		
		@Test(timeout = 1000)
		public void testgetPriceMethodInPaintingClassLogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			Class aClass = Class.forName(paintingPath);
			
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colorTypePath), "ACRYLIC");

			Constructor<?> c = Class.forName(paintingPath)
					.getConstructor(String.class, int.class ,String.class,double.class,double.class,Class.forName(colorTypePath));
			
			double w =  (Math.random() * (10 )) + 1;
			double h =  (Math.random() * (10 )) + 1;
			int expectedPrice = (int)(w*h*50);
			
			Object painting = c.newInstance("x1",1900,"A1",w,h,color);
			
			
			Method m = painting.getClass().getDeclaredMethod("getPrice");
			assertEquals(
					"wrong painitng price ,price is calculated by multiplying the area of the painting by 50",
					expectedPrice, m.invoke(painting));
			
	
		} 
		////////////////////////////////////////////////////////////
		
		
		
		
				
		///////////////// testing the statue class \\\\\\\\\\\\\\\
		@Test(timeout = 1000)
		public void testisBuyableMethodInStatueClassLogicPass() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			Class aClass = Class.forName(statuePath);
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (1900 - 1800 + 1)) + 1;
			
			Constructor<?> c = Class.forName(statuePath)
					.getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue = c.newInstance("x1",randomYear,material);
			
			
			Method m = statue.getClass().getDeclaredMethod("isBuyable");
			assertEquals(
					"Statues are buyable if they are at least 50 (50 included) years old ,you can get the age using the year of manufacture",
					true, m.invoke(statue));
			
		}
		
		@Test(timeout = 1000)
		public void testisBuyableMethodInStatueClassLogicFail() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (2024 - 2022 + 1)) + 2022;
			
			Constructor<?> c = Class.forName(statuePath)
					.getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue = c.newInstance("x1",randomYear,material);
			
			
			Method m = statue.getClass().getDeclaredMethod("isBuyable");
			assertEquals(
					"Statues are buyable if they are at least 50 (50 included) years old ,you can get the age using the year of manufacture",
					false, m.invoke(statue));
			
		}
		
		@Test(timeout = 1000)
		public void testgetPriceMethodInStatueClassMARBLELogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (2024 - 2022 + 1)) + 2022;
			
			Constructor<?> c = Class.forName(statuePath)
					.getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue = c.newInstance("x1",randomYear,material);
			int expectedPrice = 1000;
			Method m = statue.getClass().getDeclaredMethod("getPrice");
			assertEquals(
					"wrong staue price ,price of a MARBLE statue is 1000",
					expectedPrice, m.invoke(statue));
			
	
		} 
		@Test(timeout = 1000)
		public void testgetPriceMethodInStatueClassGREENDIORITELogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "GREENDIORITE");
			int randomYear = (int) (Math.random() * (2024 - 2022 + 1)) + 2022;
			
			Constructor<?> c = Class.forName(statuePath)
					.getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue = c.newInstance("x1",randomYear,material);
			int expectedPrice = 1500;
			Method m = statue.getClass().getDeclaredMethod("getPrice");
			assertEquals(
					"wrong staue price ,price of a GREENDIORITE statue is 1500",
					expectedPrice, m.invoke(statue));
			
	
		} 
		
		@Test(timeout = 1000)
		public void testgetPriceMethodInStatueClassGRANITELogic() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "GRANITE");
			int randomYear = (int) (Math.random() * (2024 - 2022 + 1)) + 2022;
			
			Constructor<?> c = Class.forName(statuePath)
					.getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue = c.newInstance("x1",randomYear,material);
			int expectedPrice = 2000;
			Method m = statue.getClass().getDeclaredMethod("getPrice");
			assertEquals(
					"wrong staue price ,price of a GRANITE statue is 2000",
					expectedPrice, m.invoke(statue));
			
	
		} 
		////////////////////////////////////////////////////////////
	
		
		
		
		///////////////// testing the Museum class \\\\\\\\\\\\\\\
		@Test(timeout = 1000)
		public void testInstanceVariablesInClub() throws Exception {

			testInstanceVariablesArePresent(Class.forName(museumPath),"maxSize", true);
			testInstanceVariablesArePresent(Class.forName(museumPath), "budget",true);
			testInstanceVariablesArePresent(Class.forName(museumPath), "artifacts", true);

		}

		@Test(timeout = 1000)
		public void testConstructorClub() throws ClassNotFoundException {
			Class[] inputs = { int.class,int.class };
			testConstructorExists(Class.forName(museumPath), inputs);
		}
		@Test(timeout = 1000)
		public void testMethodGetAllafterInClassMuseumPass() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
			Constructor<?> c = Class.forName(museumPath).getConstructor(int.class, int.class);
			int ranodomBudget = (int) (Math.random() * (10000 - 1000 + 1)) + 1000;
			int randomSize = (int) (Math.random() * (9 - 4 + 1)) + 4;
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "GREENDIORITE");
			int randomYear = (int) (Math.random() * (2024 - 2022 + 1)) + 2022;
			int diffrentyear = randomYear+5;
			Constructor<?> sc = Class.forName(statuePath).getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue1 = sc.newInstance("x1",randomYear,material);
			Object statue2 = sc.newInstance("x2",diffrentyear,material);
			Object statue3 = sc.newInstance("x3",randomYear,material);

			Object muesum = c.newInstance(randomSize,ranodomBudget);
			Field budget = Class.forName(museumPath).getDeclaredField("budget");
			Field maxSize = Class.forName(museumPath).getDeclaredField("maxSize");
			Field artifacts = Class.forName(museumPath).getDeclaredField("artifacts");
			maxSize.setAccessible(true);
			budget.setAccessible(true);
			artifacts.setAccessible(true);

			
			
			((ArrayList<Object>) artifacts.get(muesum)).add(statue1);
			((ArrayList<Object>) artifacts.get(muesum)).add(statue2);
			((ArrayList<Object>) artifacts.get(muesum)).add(statue3);
						
			Method m = muesum.getClass().getDeclaredMethod("getAllAfter",int.class);
			ArrayList<Object> oa = (ArrayList<Object>) m.invoke(muesum, randomYear);
			if(oa==null){
				fail("the ouput of the getAllAfter method should not be null,if no artifacts is found matching the condition you should return an empty arrylist");
			}
			
			assertEquals(
					"The getAllAfter method should return an arraylist with all the artifacts manifactured after the input year",
					1, oa.size());
			
			
			
			
		}
		
		@Test(timeout = 1000)
		public void testMethodGetAllafterInClassMuseumFail() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
			Constructor<?> c = Class.forName(museumPath).getConstructor(int.class, int.class);
			int ranodomBudget = (int) (Math.random() * (10000 - 1000 + 1)) + 1000;
			int randomSize = (int) (Math.random() * (9 - 4 + 1)) + 4;
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (2024 - 2022 + 1)) + 2022;
			int diffrentyear = randomYear+5;
			Constructor<?> sc = Class.forName(statuePath).getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue1 = sc.newInstance("x1",randomYear,material);
			Object statue2 = sc.newInstance("x2",randomYear,material);
			Object statue3 = sc.newInstance("x3",randomYear,material);

			Object muesum = c.newInstance(randomSize,ranodomBudget);
			Field budget = Class.forName(museumPath).getDeclaredField("budget");
			Field maxSize = Class.forName(museumPath).getDeclaredField("maxSize");
			Field artifacts = Class.forName(museumPath).getDeclaredField("artifacts");
			maxSize.setAccessible(true);
			budget.setAccessible(true);
			artifacts.setAccessible(true);

			
			
			((ArrayList<Object>) artifacts.get(muesum)).add(statue1);
			((ArrayList<Object>) artifacts.get(muesum)).add(statue2);
			((ArrayList<Object>) artifacts.get(muesum)).add(statue3);
			
			
			Method m = muesum.getClass().getDeclaredMethod("getAllAfter",int.class);
			ArrayList<Object> oa = (ArrayList<Object>) m.invoke(muesum, randomYear);
			if(oa==null){
				fail("the ouput of the getAllAfter method should not be null,if no artifacts is found matching the condition you should return an empty arrylist");
			}
			
			assertEquals(
					"The getAllAfter method should only return the artifacts manifactured after the input year",
					0, oa.size());
			
		}
		
		@Test(timeout = 1000)
		public void testMethodGetAddAtritfactInClassMuseumStatuePass() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
			Constructor<?> c = Class.forName(museumPath).getConstructor(int.class, int.class);
			int ranodomBudget = (int) (Math.random() * (50000 - 10000 + 1)) + 10000;
			int randomSize = (int) (Math.random() * (9 - 4 + 1)) + 4;
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (1900 - 1800 + 1)) + 1800;
			Constructor<?> sc = Class.forName(statuePath).getConstructor(String.class, int.class ,Class.forName(materialType));
			Object statue1 = sc.newInstance("x1",randomYear,material);

			Object muesum = c.newInstance(randomSize,ranodomBudget);
			Field oldBudget = Class.forName(museumPath).getDeclaredField("budget");
			Field maxSize = Class.forName(museumPath).getDeclaredField("maxSize");
			Field artifacts = Class.forName(museumPath).getDeclaredField("artifacts");
			maxSize.setAccessible(true);
			oldBudget.setAccessible(true);
			artifacts.setAccessible(true);

			int originalBudget = oldBudget.getInt(muesum);
			
			
			
		Method m = muesum.getClass().getDeclaredMethod("addAtritfact",Class.forName(artifactPath));
		 m.invoke(muesum, statue1);
		 m.invoke(muesum, statue1);

//			if(oa==null){
//				fail("the ouput of the getAllAfter method should not be null,if no artifacts is found matching the condition you should return an empty arrylist");
//			}
			assertEquals("the price of the artifact should be subtracted from the budget when you add an artifact to the museum",(originalBudget-2000),oldBudget.getInt(muesum));
			assertEquals(
					"the method addAtritfact should the input artifact to the arraylist of artifacts in the museum",
					2,((ArrayList<Object>) artifacts.get(muesum)).size());
		
		}
		
		@Test(timeout = 1000)
		public void testMethodGetAddAtritfactInClassMuseumPaintingPass() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
			Constructor<?> c = Class.forName(museumPath).getConstructor(int.class, int.class);
			int ranodomBudget = (int) (Math.random() * (50000 - 10000 + 1)) + 10000;
			int randomSize = (int) (Math.random() * (9 - 4 + 1)) + 4;
			
			Class aClass = Class.forName(paintingPath);
			
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colorTypePath), "ACRYLIC");

			Constructor<?> pc = Class.forName(paintingPath)
					.getConstructor(String.class, int.class ,String.class,double.class,double.class,Class.forName(colorTypePath));
			
			double w =  (Math.random() * (10 )) + 1;
			double h =  (Math.random() * (10 )) + 1;
			int expectedPrice = (int)(w*h*50);
			
			Object painting = pc.newInstance("x1",1900,"A1",w,h,color);;

			Object muesum = c.newInstance(randomSize,ranodomBudget);
			Field oldBudget = Class.forName(museumPath).getDeclaredField("budget");
			Field maxSize = Class.forName(museumPath).getDeclaredField("maxSize");
			Field artifacts = Class.forName(museumPath).getDeclaredField("artifacts");
			maxSize.setAccessible(true);
			oldBudget.setAccessible(true);
			artifacts.setAccessible(true);

			int originalBudget = oldBudget.getInt(muesum);
			
			
			
		Method m = muesum.getClass().getDeclaredMethod("addAtritfact",Class.forName(artifactPath));
		 m.invoke(muesum, painting);
		 m.invoke(muesum, painting);
		

//			if(oa==null){
//				fail("the ouput of the getAllAfter method should not be null,if no artifacts is found matching the condition you should return an empty arrylist");
//			}
			assertEquals("the price of the artifact should be subtracted from the budget when you add an artifact to the museum",(originalBudget-(expectedPrice*2)),oldBudget.getInt(muesum));
			assertEquals(
					"the method addAtritfact should the input artifact to the arraylist of artifacts in the museum",
					2,((ArrayList<Object>) artifacts.get(muesum)).size());
		
		}
		
		@Test(timeout = 1000)
		public void testMethodGetAddAtritfactInClassMuseumFail1() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Constructor<?> c = Class.forName(museumPath).getConstructor(int.class, int.class);
			int ranodomBudget = (int) (Math.random() * (50000 - 10000 + 1)) + 10000;
			int randomSize = (int) (Math.random() * (9 - 4 + 1)) + 4;
			
			Class aClass = Class.forName(paintingPath);
			
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colorTypePath), "CHARCOAL");
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (2024 - 2022 + 1)) + 2022;
			Constructor<?> sc = Class.forName(statuePath).getConstructor(String.class, int.class ,Class.forName(materialType));

			Constructor<?> pc = Class.forName(paintingPath)
					.getConstructor(String.class, int.class ,String.class,double.class,double.class,Class.forName(colorTypePath));
			
			double w =  (Math.random() * (10 )) + 1;
			double h =  (Math.random() * (10 )) + 1;
			int expectedPrice = (int)(w*h*50);
			
			Object painting = pc.newInstance("x1",1900,"A1",w,h,color);
			Object statue1 = sc.newInstance("x1",randomYear,material);
			Object muesum = c.newInstance(randomSize,ranodomBudget);
			Method m = muesum.getClass().getDeclaredMethod("addAtritfact",Class.forName(artifactPath));
			//Class<?> notEnoughBudgetException = Class.forName(cannotBuyPath);

			
			try {
				m.invoke(muesum,painting );
				fail("Expected cannotBuyException was not thrown,the exception should be thrown if the artifact is not buyable");

			} catch (InvocationTargetException e) {
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue(
						"Expected cannotBuyException was not thrown,the exception should be thrown if the artifact is not buyable",
						Class.forName(cannotBuyPath).isInstance(thrownException));
			}
			
			try {
				m.invoke(muesum,statue1 );
				fail("Expected cannotBuyException was not thrown,the exception should be thrown if the artifact is not buyable");

			} catch (InvocationTargetException e) {
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue(
						"Expected cannotBuyException was not thrown,the exception should be thrown if the artifact is not buyable",
						Class.forName(cannotBuyPath).isInstance(thrownException));
			}
		}
		
		@Test(timeout = 1000)
		public void testMethodGetAddAtritfactInClassMuseumFail2() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException  {
			Constructor<?> c = Class.forName(museumPath).getConstructor(int.class, int.class);
			int ranodomBudget = (int) (Math.random() * (10 - 1 + 1)) + 1;
			int randomSize = (int) (Math.random() * (9 - 4 + 1)) + 4;
			
			Class aClass = Class.forName(paintingPath);
			
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colorTypePath), "ACRYLIC");
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (1900 - 1800 + 1)) + 1800;
			Constructor<?> sc = Class.forName(statuePath).getConstructor(String.class, int.class ,Class.forName(materialType));

			Constructor<?> pc = Class.forName(paintingPath)
					.getConstructor(String.class, int.class ,String.class,double.class,double.class,Class.forName(colorTypePath));
			
			double w =  (Math.random() * (10 )) + 1;
			double h =  (Math.random() * (10 )) + 1;
			int expectedPrice = (int)(w*h*50);
			
			Object painting = pc.newInstance("x1",1900,"A1",w,h,color);
			Object statue1 = sc.newInstance("x1",randomYear,material);
			Object muesum = c.newInstance(randomSize,ranodomBudget);
			Method m = muesum.getClass().getDeclaredMethod("addAtritfact",Class.forName(artifactPath));
			//Class<?> notEnoughBudgetException = Class.forName(cannotBuyPath);

			
			try {
				m.invoke(muesum,painting );
				fail("Expected NoEnoughMoneyException was not thrown,the exception should be thrown if your budget is less than the artifact price when you try to add it");

			} catch (InvocationTargetException e) {
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue(
						"Expected NoEnoughMoneyException was not thrown,the exception should be thrown if your budget is less than the artifact price when you try to add it",
						Class.forName(noEnoughMoneyPath).isInstance(thrownException));
			}
			
			try {
				m.invoke(muesum,statue1 );
				fail("Expected NoEnoughMoneyException was not thrown,the exception should be thrown if your budget is less than the artifact price when you try to add it");

			} catch (InvocationTargetException e) {
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue(
						"Expected NoEnoughMoneyException was not thrown,the exception should be thrown if your budget is less than the artifact price when you try to add it",
						Class.forName(noEnoughMoneyPath).isInstance(thrownException));
			}
		
		}
		
		@Test(timeout = 1000)
		public void testMethodGetAddAtritfactInClassMuseumFail3() throws ClassNotFoundException, NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
			Constructor<?> c = Class.forName(museumPath).getConstructor(int.class, int.class);
			int ranodomBudget = (int) (Math.random() * (50000 - 10000 + 1)) + 10000;
			int randomSize = 0;
			
			Class aClass = Class.forName(paintingPath);
			
			Object color = Enum.valueOf((Class<Enum>) Class.forName(colorTypePath), "ACRYLIC");
			
			Object material = Enum.valueOf((Class<Enum>) Class.forName(materialType), "MARBLE");
			int randomYear = (int) (Math.random() * (1900 - 1800 + 1)) + 1800;
			Constructor<?> sc = Class.forName(statuePath).getConstructor(String.class, int.class ,Class.forName(materialType));

			Constructor<?> pc = Class.forName(paintingPath)
					.getConstructor(String.class, int.class ,String.class,double.class,double.class,Class.forName(colorTypePath));
			
			double w =  (Math.random() * (10 )) + 1;
			double h =  (Math.random() * (10 )) + 1;
			int expectedPrice = (int)(w*h*50);
			
			Object painting = pc.newInstance("x1",1900,"A1",w,h,color);
			Object statue1 = sc.newInstance("x1",randomYear,material);
			Object muesum = c.newInstance(randomSize,ranodomBudget);
			Method m = muesum.getClass().getDeclaredMethod("addAtritfact",Class.forName(artifactPath));
			//Class<?> notEnoughBudgetException = Class.forName(cannotBuyPath);

			
			try {
				m.invoke(muesum,painting );
				fail("Expected NoEnoughSpaceException was not thrown,the exception should be thrown if the size of the artifacts array is beyond the maximum size");

			} catch (InvocationTargetException e) {
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue(
						"Expected NoEnoughMoneyException was not thrown,the exception should be thrown if the size of the artifacts array is beyond the maximum size",
						Class.forName(noEnoughSpacePath).isInstance(thrownException));
			}
			
			try {
				m.invoke(muesum,statue1 );
				fail("Expected NoEnoughMoneyException was not thrown,the exception should be thrown if the size of the artifacts array is beyond the maximum size");

			} catch (InvocationTargetException e) {
				Throwable thrownException = e.getTargetException();
				assertNotNull("Expected exception was thrown", thrownException);
				assertTrue(
						"Expected NoEnoughMoneyException was not thrown,the exception should be thrown if the size of the artifacts array is beyond the maximum size",
						Class.forName(noEnoughSpacePath).isInstance(thrownException));
			}
		
		}
		}
		
		
		
		////////////////////////////////////////////////////////////
		
	
		


