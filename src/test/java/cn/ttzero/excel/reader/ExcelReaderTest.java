/*
 * Copyright (c) 2019, guanquan.wang@yandex.com All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.ttzero.excel.reader;

import cn.ttzero.excel.Print;
import cn.ttzero.excel.manager.ExcelType;
import cn.ttzero.excel.util.FileUtil;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import static cn.ttzero.excel.Print.println;
import static cn.ttzero.excel.Print.print;

/**
 * Create by guanquan.wang at 2019-04-26 17:42
 */
public class ExcelReaderTest {
    public static Path testResourceRoot() {
        URL url = ExcelReaderTest.class.getClassLoader().getResource(".");
        if (url == null) {
            throw new RuntimeException("Load test resources error.");
        }
        return FileUtil.isWindows()
            ? Paths.get(url.getFile().substring(1))
            : Paths.get(url.getFile());
    }

    @Test public void testReader() {
        try (ExcelReader reader = ExcelReader.read(testResourceRoot().resolve("1.xlsx"))) {
            assert reader.getType() == ExcelType.XLSX;

            AppInfo appInfo = reader.getAppInfo();
            assert "对象数组测试".equals(appInfo.getTitle());
            assert "guanquan.wang".equals(appInfo.getCreator());
            println(appInfo);

            reader.sheet(0).rows().forEach(Print::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void testColumnIndex() {
        try (ExcelReader reader = ExcelReader.read(testResourceRoot().resolve("1.xlsx"))) {
            Sheet sheet = reader.sheet(0);
            for (Iterator<Row> it = sheet.iterator(); it.hasNext();) {
                Row row = it.next();
                println(row.getRowNumber()
                    + " | " + row.getFirstColumnIndex()
                    + " | " + row.getLastColumnIndex()
                    + " => " + row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void testReset() {
        try (ExcelReader reader = ExcelReader.read(testResourceRoot().resolve("1.xlsx"))) {

            Sheet sheet = reader.sheet(0);
            sheet.rows().forEach(Print::println);

            println("------------------");

            sheet.reset(); // Reset the row index to begging

            sheet.rows().forEach(Print::println);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test public void testForEach() {
        try (ExcelReader reader = ExcelReader.read(testResourceRoot().resolve("1.xlsx"))) {
            Sheet sheet = reader.sheet(0);

            Row header = sheet.getHeader();

            for (Iterator<Row> it = sheet.iterator(); it.hasNext(); ) {
                Row row = it.next();
                if (row.getRowNumber() == 0) continue;

                print(row.getRowNumber());
                for (int start = 0, end = row.getLastColumnIndex(); start < end; start++) {
                    print(header.getString(start));
                    print(" : ");
                    print(row.getString(start));
                    print(' ');
                }
                println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
