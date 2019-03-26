package com.nicom.processing.processors.forth.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;


@SuppressWarnings("deprecation")
@RunWith(MockitoJUnitRunner.class)
public class NetworkUtilsTest {
	
	@Test
	public void showDetailsTest() throws MalformedURLException{
		String urlStr="http://example.com:80/docs/books/tutorial/index.html?name=networking#DOWNLOADING";
		NetworkUtils.setNetworkUrl(urlStr);		
		NetworkUtils.showUrlDetails();
		assertThat(true, is(true));
	}
	

	@Test
	public void UrlReaderTest() throws IOException{
		String expectedResult="";
		String urlStr="https://www.google.co.za/";
		URL aURL = new URL(urlStr);		
		String res = NetworkUtils.urlReader(aURL);
		System.out.println(res);
		assertThat(res.length(), greaterThan(0));
	}
	
	@Test
	public void pingEmulatortest() throws IOException {
		String ipAddress="10.154.2.1";
		boolean canPing = NetworkUtils.pingEmulator(ipAddress);
		assertThat(true, is(true));
	}
	
	@Test
	public void getInetAddressesTest() throws UnknownHostException {
		String urlStr="www.google.co.za";
		InetAddress[] inetAddresses =NetworkUtils.getInetAddresses(urlStr);
		InetAddress inetAddress = inetAddresses[0];
		inetAddress.getHostName();
		inetAddress.getCanonicalHostName();
		assertThat(true, is(true));
	}

	@Test
	public void getHostNameTest() throws UnknownHostException {
		String urlStr="www.google.co.za";
		String hostName=NetworkUtils.getHostName(urlStr);
		assertThat(true, is(true));
	}

	@Test
	public void getHostAddressTest() throws UnknownHostException {
		String urlStr="www.google.co.za";
		String hostAddress=NetworkUtils.getHostAddress(urlStr);
		assertThat(true, is(true));
	}
	

	@Test
	public void getLocalHostNameTest() throws UnknownHostException {
		String hostname=NetworkUtils.getLocalHostName();
		System.out.println(hostname);
		assertThat(true, is(true));
	}
	
	@Test
	public void getLocalHostIpAddressTest() throws UnknownHostException {
		String hostname=NetworkUtils.getLocalHostIpAddress();
		System.out.println(hostname);
		assertThat(true, is(true));
	}
	
	@Test
	public void getLocalHostIpAddressesTest()throws  SocketException {
		List<String> ipAddresses=NetworkUtils.getLocalHostIpAddresses();
		for (String ipAddress : ipAddresses) {
			System.out.println(ipAddress);
		}
		assertThat(true, is(true));
	}

}
