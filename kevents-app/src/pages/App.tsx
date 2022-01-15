import { AlignLeftOutlined, PlusOutlined } from "@ant-design/icons";
import { Menu } from "antd";
import Layout, { Content, Footer } from "antd/lib/layout/layout";
import Sider from "antd/lib/layout/Sider";
import Text from "antd/lib/typography/Text";
import React, { useState } from "react";
import { MENU_OPTIONS } from "../constants/MenuConstants";
import "./App.css";

const App = () => {
  const [collapsed, setCollapse] = useState(false);
  const [defaultOption, setDefaultOption] = useState(MENU_OPTIONS.CREATE);

  return (
    <Layout className="layout">
      <Sider
        collapsible
        collapsed={collapsed}
        onCollapse={() => setCollapse(!collapsed)}
      >
        <div className="logo">
          <Text id="appName" className="appName">
            KEvents
          </Text>
        </div>
        <Menu theme="dark" defaultSelectedKeys={[defaultOption]} mode="inline">
          <Menu.Item
            id="newEventOption"
            key={MENU_OPTIONS.CREATE}
            icon={<PlusOutlined />}
            onClick={() => setDefaultOption(MENU_OPTIONS.CREATE)}
          >
            New event
          </Menu.Item>
          <Menu.Item
            id="listEventsOption"
            key={MENU_OPTIONS.LIST}
            icon={<AlignLeftOutlined />}
            onClick={() => setDefaultOption(MENU_OPTIONS.LIST)}
          >
            List events
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout className="site-layout">
        <Content className="content">
          {defaultOption === MENU_OPTIONS.CREATE ? (
            <p>This is text 1</p>
          ) : (
            <p>This is text 2</p>
          )}
        </Content>
        <Footer id="footer" className="footer">
          ©2022 Created by Thomas Perron
        </Footer>
      </Layout>
    </Layout>
  );
};

export default App;
