package org.nhindirect.config.resources;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.nhindirect.config.BaseTestPlan;
import org.nhindirect.config.ConfigServiceRunner;
import org.nhindirect.config.TestUtils;
import org.nhindirect.config.model.Setting;
import org.nhindirect.config.store.dao.SettingDao;

import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class SettingResource_updateSettingTest 
{
	   protected SettingDao settingDao;
	    
		static WebResource resource;
		
		abstract class TestPlan extends BaseTestPlan 
		{
			protected Setting addedSetting;
			
			@Override
			protected void setupMocks()
			{
				try
				{
					settingDao = (SettingDao)ConfigServiceRunner.getSpringApplicationContext().getBean("settingDao");
					
					resource = 	getResource(ConfigServiceRunner.getConfigServiceURL());		
				}
				catch (Throwable t)
				{
					throw new RuntimeException(t);
				}
			}
			
			@Override
			protected void tearDownMocks()
			{

			}
			
			protected Setting getSettingToAdd()
			{

				addedSetting = new Setting();
				addedSetting.setName("setting1");
				addedSetting.setValue("value1");
				return addedSetting;
			}
			
			protected abstract String getSettingNameToUpdate();
			
			protected abstract String getSettingValueToUpdate();
			
			@Override
			protected void performInner() throws Exception
			{				
				
				final Setting addSetting = getSettingToAdd();
				
				if (addSetting != null)
				{
					try
					{
						resource.path("/api/setting/" + TestUtils.uriEscape(addSetting.getName()) + "/" + TestUtils.uriEscape(addSetting.getValue())).put();
					}
					catch (UniformInterfaceException e)
					{
						throw e;
					}
				}
				
				
				try
				{
					resource.path("/api/setting/" + TestUtils.uriEscape(getSettingNameToUpdate()) + "/" + TestUtils.uriEscape(getSettingValueToUpdate())).post();
				}
				catch (UniformInterfaceException e)
				{
					throw e;
				}
				
				final Setting setting = resource.path("/api/setting/" + TestUtils.uriEscape(getSettingNameToUpdate())).get(Setting.class);

				doAssertions(setting);

				
			}
				
			protected void doAssertions(Setting setting) throws Exception
			{
				
			}
		}
		
		@Test
		public void testUpdateSetting_updateExistingSetting_assertSettingUpdated() throws Exception
		{
			new TestPlan()
			{

				@Override
				protected String getSettingNameToUpdate()
				{
					return "setting1";
				}
				
				protected String getSettingValueToUpdate()
				{
					return "value2";
				}
				
				@Override
				protected void doAssertions(Setting setting) throws Exception
				{
					assertEquals("setting1", setting.getName());
					assertEquals("value2", setting.getValue());
				}
			}.perform();
		}	
		
		@Test
		public void testUpdateSetting_settingNotFound_assertNotFound() throws Exception
		{
			new TestPlan()
			{

				@Override
				protected String getSettingNameToUpdate()
				{
					return "setting2";
				}
				
				protected String getSettingValueToUpdate()
				{
					return "value2";
				}
				
				@Override
				protected void assertException(Exception exception) throws Exception 
				{
					assertTrue(exception instanceof UniformInterfaceException);
					UniformInterfaceException ex = (UniformInterfaceException)exception;
					assertEquals(404, ex.getResponse().getStatus());
				}
			}.perform();
		}		
		
		@Test
		public void testUpdateSetting_errorInLookup_assertServiceError() throws Exception
		{
			new TestPlan()
			{

				protected SettingResource settingService;
				
				@Override
				protected void setupMocks()
				{
					try
					{
						super.setupMocks();
						
						settingService = (SettingResource)ConfigServiceRunner.getSpringApplicationContext().getBean("settingResource");

						SettingDao mockDAO = mock(SettingDao.class);
						doThrow(new RuntimeException()).when(mockDAO).getByNames(Arrays.asList("setting1"));
						
						settingService.setSettingDao(mockDAO);
					}
					catch (Throwable t)
					{
						throw new RuntimeException(t);
					}
				}
				
				@Override
				protected void tearDownMocks()
				{
					super.tearDownMocks();
					
					settingService.setSettingDao(settingDao);
				}	
				
				protected Setting getSettingToAdd()
				{
					return null;
				}
				
				@Override
				protected String getSettingNameToUpdate()
				{
					return "setting1";
				}
				
				protected String getSettingValueToUpdate()
				{
					return "value2";
				}
				
				@Override
				protected void assertException(Exception exception) throws Exception 
				{
					assertTrue(exception instanceof UniformInterfaceException);
					UniformInterfaceException ex = (UniformInterfaceException)exception;
					assertEquals(500, ex.getResponse().getStatus());
				}
			}.perform();
		}
		
		@Test
		public void testUpdateSetting_errorInUpdate_assertServiceError() throws Exception
		{
			new TestPlan()
			{

				protected SettingResource settingService;
				
				@Override
				protected void setupMocks()
				{
					try
					{
						super.setupMocks();
						
						settingService = (SettingResource)ConfigServiceRunner.getSpringApplicationContext().getBean("settingResource");

						SettingDao mockDAO = mock(SettingDao.class);
						org.nhindirect.config.store.Setting setting = new org.nhindirect.config.store.Setting();
						when(mockDAO.getByNames(Arrays.asList("setting1"))).thenReturn(Arrays.asList(setting));
						doThrow(new RuntimeException()).when(mockDAO).update(eq("setting1"), eq("value2"));
						
						settingService.setSettingDao(mockDAO);
					}
					catch (Throwable t)
					{
						throw new RuntimeException(t);
					}
				}
				
				@Override
				protected void tearDownMocks()
				{
					super.tearDownMocks();
					
					settingService.setSettingDao(settingDao);
				}	
				
				protected Setting getSettingToAdd()
				{
					return null;
				}
				
				@Override
				protected String getSettingNameToUpdate()
				{
					return "setting1";
				}
				
				protected String getSettingValueToUpdate()
				{
					return "value2";
				}
				
				@Override
				protected void assertException(Exception exception) throws Exception 
				{
					assertTrue(exception instanceof UniformInterfaceException);
					UniformInterfaceException ex = (UniformInterfaceException)exception;
					assertEquals(500, ex.getResponse().getStatus());
				}
			}.perform();
		}		
}
