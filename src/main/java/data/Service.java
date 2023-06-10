package data;

import java.sql.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import data.Wifi;

public class Service {
	String DBFileName = "C:\\dev\\sqlite-tools-win32-x86-3420000\\Wifi.sqlite3";
	
	/**
	 * DB 연결 Connection
	 * @return connection
	 */
	public Connection getConn() {
		Connection conn = null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + DBFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	
	/**
	 * Open API 에서 와이파이 데이터 가져와 DB에 저장
	 * @param list
	 * @throws Exception
	 */
	public void load(List<Map<String, String>> list) throws Exception{
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			conn = getConn();
			String sql = "insert or replace into wifi_info "
					+ "(WIFI_ID, WRDOFC, WIFI_NAME, ADRES1, ADRES2, FLOOR, INSTL_TY, INSTL_MBY, SVC_SE, NET_TYPE, CNSTC_YEAR, IN_OUT, REMARS3, LAT, LNT, WORK_DT) "
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			
			conn.setAutoCommit(false);
			
			ps = conn.prepareStatement(sql);
			
			List<Map<String, String>> mapList = list;
			for (int i = 0; i < mapList.size(); i++) {
				ps.setString(1, mapList.get(i).get("X_SWIFI_MGR_NO"));
				ps.setString(2, mapList.get(i).get("X_SWIFI_WRDOFC"));
				ps.setString(3, mapList.get(i).get("X_SWIFI_MAIN_NM"));
				ps.setString(4, mapList.get(i).get("X_SWIFI_ADRES1"));
				ps.setString(5, mapList.get(i).get("X_SWIFI_ADRES2"));
				ps.setString(6, mapList.get(i).get("X_SWIFI_INSTL_FLOOR"));
				ps.setString(7, mapList.get(i).get("X_SWIFI_INSTL_TY"));
				ps.setString(8, mapList.get(i).get("X_SWIFI_INSTL_MBY"));
				ps.setString(9, mapList.get(i).get("X_SWIFI_SVC_SE"));
				ps.setString(10, mapList.get(i).get("X_SWIFI_CMCWR"));
				ps.setString(11, mapList.get(i).get("X_SWIFI_CNSTC_YEAR"));
				ps.setString(12, mapList.get(i).get("X_SWIFI_INOUT_DOOR"));
				ps.setString(13, mapList.get(i).get("X_SWIFI_REMARS3"));
				if (Double.parseDouble(String.valueOf(mapList.get(i).get("LAT"))) < Double.parseDouble(String.valueOf(mapList.get(i).get("LNT")))) {
					ps.setDouble(14, Double.parseDouble(String.valueOf(mapList.get(i).get("LAT"))));
					ps.setDouble(15, Double.parseDouble(String.valueOf(mapList.get(i).get("LNT"))));
				} else {
					ps.setDouble(14, Double.parseDouble(String.valueOf(mapList.get(i).get("LNT"))));
					ps.setDouble(15, Double.parseDouble(String.valueOf(mapList.get(i).get("LAT"))));
					
				}
				ps.setString(16, mapList.get(i).get("WORK_DTTM"));
				ps.addBatch();
                ps.clearParameters();
				
				if ((i%100) == 0) {
					ps.executeBatch();
					ps.clearBatch();
					conn.commit();
				}
			}
			ps.executeBatch();
			conn.commit();
			conn.setAutoCommit(true);
			
		} catch (Exception e) {
			conn.rollback();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
	}

	
	/**
	 * 근처 와이파이 20개 조회
	 * @param lat
	 * @param lnt
	 * @return 거리 가까운 순 와이파이 정보 20개 리스트
	 */
	public List<Wifi> showList(double lat, double lnt) {
		List<Wifi> wifiList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			conn = getConn();
			String sql = "select * "
					+ ", (6371 * acos(cos(radians(?))*cos(radians(LAT))*cos(radians(LNT) "
					+ "- radians(?)) + sin(radians(?))*sin(radians(LAT)))) as distance "
					+ "from wifi_info "
					+ "order by distance "
					+ "limit 20;";
			
			ps = conn.prepareStatement(sql);
			ps.setDouble(1, lat);
			ps.setDouble(2, lnt);
			ps.setDouble(3, lat);
			
			rs = ps.executeQuery();

            while (rs.next()) {
                String wifiId = rs.getString("WIFI_ID");
                String wrdofc = rs.getString("WRDOFC");
                String wifiName = rs.getString("WIFI_NAME");
                String adres1 = rs.getString("ADRES1");
                String adres2 = rs.getString("ADRES2");
                String floor = rs.getString("FLOOR");
                String instlTy = rs.getString("INSTL_TY");
                String instlMby = rs.getString("INSTL_MBY");
                String svcSe = rs.getString("SVC_SE");
                String cmcwr = rs.getString("NET_TYPE");
                String cnstcYear = rs.getString("CNSTC_YEAR");
                String inOut = rs.getString("IN_OUT");
                String remars3 = rs.getString("REMARS3");
                double rslat = rs.getDouble("LAT");
                double rslnt = rs.getDouble("LNT");
                String workDt = rs.getString("WORK_DT");
                double distance = rs.getDouble("distance");
 
                Wifi wifi = new Wifi(distance);
                wifi.setWifiId(wifiId);
                wifi.setWrdofc(wrdofc);
                wifi.setWifiName(wifiName);
                wifi.setAdres1(adres1);
                wifi.setAdres2(adres2);
                wifi.setFloor(floor);
                wifi.setInstlTy(instlTy);
                wifi.setInstlMby(instlMby);
                wifi.setSvcSe(svcSe);
                wifi.setCmcwr(cmcwr);
                wifi.setCnstcYear(cnstcYear);
                wifi.setInOut(inOut);
                wifi.setRemars3(remars3);
                wifi.setLat(rslat);
                wifi.setLnt(rslnt);
                wifi.setWorkDt(workDt);
                
                wifiList.add(wifi);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return wifiList;
	
	}

	/**
	 * 와이파이 상세 정보 조회
	 * @param WifiId
	 * @return 와이파이 정보 객체
	 */
	public Wifi showDetail(String WifiId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Wifi wifi = new Wifi();
		
		try {
			conn = getConn();
			String sql = "select * "
					+ "from WIFI_INFO "
					+ "where  WIFI_ID = ?; ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, WifiId);
			
			rs = ps.executeQuery();

            if (rs.next()) {
                String wifiId = rs.getString("WIFI_ID");
                String wrdofc = rs.getString("WRDOFC");
                String wifiName = rs.getString("WIFI_NAME");
                String adres1 = rs.getString("ADRES1");
                String adres2 = rs.getString("ADRES2");
                String floor = rs.getString("FLOOR");
                String instlTy = rs.getString("INSTL_TY");
                String instlMby = rs.getString("INSTL_MBY");
                String svcSe = rs.getString("SVC_SE");
                String cmcwr = rs.getString("NET_TYPE");
                String cnstcYear = rs.getString("CNSTC_YEAR");
                String inOut = rs.getString("IN_OUT");
                String remars3 = rs.getString("REMARS3");
                double rslat = rs.getDouble("LAT");
                double rslnt = rs.getDouble("LNT");
                String workDt = rs.getString("WORK_DT");
 
                wifi.setWifiId(wifiId);
                wifi.setWrdofc(wrdofc);
                wifi.setWifiName(wifiName);
                wifi.setAdres1(adres1);
                wifi.setAdres2(adres2);
                wifi.setFloor(floor);
                wifi.setInstlTy(instlTy);
                wifi.setInstlMby(instlMby);
                wifi.setSvcSe(svcSe);
                wifi.setCmcwr(cmcwr);
                wifi.setCnstcYear(cnstcYear);
                wifi.setInOut(inOut);
                wifi.setRemars3(remars3);
                wifi.setLat(rslat);
                wifi.setLnt(rslnt);
                wifi.setWorkDt(workDt);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return wifi;
	
	}
	
	
	/**
	 * 히스토리 조회
	 * @return 히스토리 정보 리스트
	 */
	public List<String[]> selectH() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String[]> historys = new ArrayList<>();
		
		try {
			conn = getConn();
			String sql = "select * "
					+ "from SEARCH_HISTORY "
					+ "order by HIS_ID desc; ";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();

            while (rs.next()) {
                String hisId = rs.getString("HIS_ID");
                String lat = rs.getString("LAT");
                String lnt = rs.getString("LNT");
                String searchDt = rs.getString("SEARCH_DT");
 
                String[] strs = new String[] {hisId, lat, lnt, searchDt};
                historys.add(strs);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return historys;
	
	}

	/**
	 * 히스토리 저장
	 * @param lat
	 * @param lnt
	 */
	public void insertH(double lat, double lnt) {
		Connection conn = null;
		PreparedStatement ps = null;
		int r = 0;
		LocalDateTime now = LocalDateTime.now();
		
		try {
			conn = getConn();
			String sql = "insert into SEARCH_HISTORY "
					+ "(LAT, LNT, SEARCH_DT) "
					+ "values(?,?,?);";
			
			ps = conn.prepareStatement(sql);
			
			ps.setDouble(1, lat);
			ps.setDouble(2, lnt);
			ps.setString(3, now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			r = ps.executeUpdate();
			
			if (r > 0) {
				System.out.println("저장 성공");
			} else {
				System.out.println("저장 실패");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
	}

	/**
	 * 히스토리 삭제
	 * @param hisId
	 * @return 성공/실패 여부
	 */
	public int deleteH(String hisId) {
		Connection conn = null;
		PreparedStatement ps = null;
		LocalDateTime now = LocalDateTime.now();
		int r = 0;
		
		try {
			conn = getConn();
			String sql = "delete from SEARCH_HISTORY "
					+ "where HIS_ID = ?; ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(hisId));
			
			r = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return r > 0 ? 1 : 0;
	}


	/**
	 * 북마크 그룹 조회(전체)
	 * @return 북마크 그룹 정보 리스트
	 */
	public List<String[]> selectG() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String[]> groups = new ArrayList<>();
		
		try {
			conn = getConn();
			String sql = "select * "
					+ "from BOOKMARK_GROUP "
					+ "order by MARK_ORDER; ";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();

            while (rs.next()) {
                String markId = rs.getString("MARK_ID");
                String markName = rs.getString("MARK_NAME");
                String markOrder = rs.getString("MARK_ORDER");
                String regDt = rs.getString("REG_DT");
                String chanDt = rs.getString("CHAN_DT") == null ? "" : rs.getString("CHAN_DT");
 
                String[] strs = new String[] {markId, markName, markOrder, regDt, chanDt};
                groups.add(strs);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return groups;
	
	}

	/**
	 * 북마크 그룹 조회(선택된 그룹)
	 * @param markId
	 * @return 북마크 그룹 이름, 순서 정보 배열
	 */
	public String[] selectGDetail(String markId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] strs = null;
		
		try {
			conn = getConn();
			String sql = "select MARK_NAME, MARK_ORDER "
					+ "from BOOKMARK_GROUP "
					+ "where MARK_ID = ?; ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(markId));
			
			rs = ps.executeQuery();

            if (rs.next()) {
                String markName = rs.getString("MARK_NAME");
                String markOrder = rs.getString("MARK_ORDER");
                
                strs = new String[] {markName, markOrder};
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return strs;
	
	}

	/**
	 * 북마크 그룹 추가
	 * @param markName
	 * @param markOrder
	 * @return 성공/실패 여부
	 */
	public int insertG(String markName, String markOrder) {
		Connection conn = null;
		PreparedStatement ps = null;
		int r = 0;
		LocalDateTime now = LocalDateTime.now();
		
		try {
			conn = getConn();
			String sql = "insert into BOOKMARK_GROUP "
					+ "(MARK_NAME, MARK_ORDER, REG_DT) "
					+ "values(?,?,?);";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, markName);
			ps.setInt(2, Integer.parseInt(markOrder));
			ps.setString(3, now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			r = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return r > 0 ? 1 : 0;
	}

	/**
	 * 북마크 그룹 수정
	 * @param markId
	 * @param markName
	 * @param markOrder
	 * @return 성공/실패 여부
	 */
	public int updateG(String markId, String markName, String markOrder) {
		Connection conn = null;
		PreparedStatement ps = null;
		int r = 0;
		LocalDateTime now = LocalDateTime.now();
		
		try {
			conn = getConn();
			
			String sql = "update BOOKMARK_GROUP set MARK_NAME = ?, MARK_ORDER = ?, CHAN_DT = ? "
					+ "where MARK_ID = ?; ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, markName);
			ps.setInt(2, Integer.parseInt(markOrder));
			ps.setString(3, now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			ps.setInt(4, Integer.parseInt(markId));
			
			r = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return r > 0 ? 1 : 0;
	}

	/**
	 * 북마크 그룹 삭제
	 * @param markName
	 * @return 성공/실패 여부
	 */
	public int deleteG(String markName) {
		Connection conn = null;
		PreparedStatement ps = null;
		int r = 0;
		
		try {
			conn = getConn();
			String sql = "delete from BOOKMARK_GROUP "
					+ "where MARK_NAME = ?; ";
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, markName);
			
			r = ps.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return r > 0 ? 1 : 0;
	}


	/**
	 * 북마크 조회(전체)
	 * @return 북마크 정보 리스트
	 */
	public List<String[]> selectM() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<String[]> marks = new ArrayList<>();
		
		try {
			conn = getConn();
			String sql = "select g.MARK_ID, g.MARK_NAME, i.WIFI_ID, i.WIFI_NAME, m.REG_DT, m.ID "
					+ "from BOOKMARK as m, WIFI_INFO as i, BOOKMARK_GROUP as g "
					+ "where g.MARK_ID = m.MARK_ID and i.WIFI_ID = m.WIFI_ID "
					+ "order by g.MARK_ORDER; ";
			
			ps = conn.prepareStatement(sql);
			
			rs = ps.executeQuery();

            while (rs.next()) {
                String markId = rs.getString("MARK_ID");
                String markName = rs.getString("MARK_NAME");
                String wifiName = rs.getString("WIFI_NAME");
                String regDt = rs.getString("REG_DT");
                String wifiId = rs.getString("WIFI_ID");
                String id = rs.getString("ID");
 
                String[] strs = new String[] {id, markName, wifiName, regDt, markId, wifiId};
                marks.add(strs);
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return marks;
	
	}

	/**
	 * 북마크 조회(선택된 북마크)
	 * @param markId
	 * @param wifiId
	 * @return 북마크 그룹 이름, 와이파이 이름, 등록일자, 북마크 번호 정보 배열
	 */
	public String[] selectM(String markId, String wifiId) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String[] mark = null;
		
		try {
			conn = getConn();
			String sql = "select g.MARK_ID, g.MARK_NAME, i.WIFI_ID, i.WIFI_NAME, m.REG_DT, m.ID "
					+ "from BOOKMARK as m, WIFI_INFO as i, BOOKMARK_GROUP as g "
					+ "where m.MARK_ID = ? and m.WIFI_ID = ? and g.MARK_ID = m.MARK_ID and i.WIFI_ID = m.WIFI_ID; ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(markId));
			ps.setString(2, wifiId);
			
			rs = ps.executeQuery();

            if (rs.next()) {
                String markName = rs.getString("MARK_NAME");
                String wifiName = rs.getString("WIFI_NAME");
                String regDt = rs.getString("REG_DT");
                String id = rs.getString("ID");
 
                mark = new String[] {markName, wifiName, regDt, id};
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return mark;
	
	}

	/**
	 * 북마크 추가
	 * @param markId
	 * @param wifiId
	 * @return 성공/실패 여부
	 */
	public int insertM(String markId, String wifiId) {
		Connection conn = null;
		PreparedStatement ps = null;
		int r = 0;
		LocalDateTime now = LocalDateTime.now();
		
		try {
			conn = getConn();
			String sql = "insert into BOOKMARK "
					+ "(MARK_ID, WIFI_ID, REG_DT) "
					+ "values(?,?,?);";
			
			ps = conn.prepareStatement(sql);
			
			ps.setInt(1, Integer.parseInt(markId));
			ps.setString(2, wifiId);
			ps.setString(3, now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
			
			r = ps.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return r > 0 ? 1 : 0;
	}

	/**
	 * 북마크 삭제
	 * @param regDt
	 * @return 성공/실패 여부
	 */
	public int deleteM(String id) {
		Connection conn = null;
		PreparedStatement ps = null;
		int r = 0;
		
		try {
			conn = getConn();
			String sql = "delete from BOOKMARK "
					+ "where ID = ?; ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(id));
			
			r = ps.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return r > 0 ? 1 : 0;
	}

	/**
	 * 북마크 삭제(북마크 그룹 삭제시)
	 * @param markId
	 * @return 성공/실패 여부
	 */
	public int deleteMarks(String markId) {
		Connection conn = null;
		PreparedStatement ps = null;
		int r = 0;
		
		try {
			conn = getConn();
			String sql = "delete from BOOKMARK "
					+ "where mark_id = ?; ";
			
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(markId));
			
			r = ps.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

            try {
                if (ps != null && !ps.isClosed()) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
		}
		return r > 0 ? 1 : 0;
	}

}