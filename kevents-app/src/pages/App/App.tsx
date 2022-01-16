import { AlignLeftOutlined, PlusOutlined } from "@ant-design/icons";
import { Menu } from "antd";
import Layout, { Content, Footer } from "antd/lib/layout/layout";
import Sider from "antd/lib/layout/Sider";
import Text from "antd/lib/typography/Text";
import React, { useEffect, useState } from "react";
import Events from "../../components/events/Events";
import NewEventForm from "../../components/newEventForm/NewEventForm";
import { clearEvents } from "../../features/eventsSlice";
import "./App.css";

const App = () => {
  const menuOptions = {
    CREATE: "1",
    LIST: "2",
  };

  const [collapsed, setCollapse] = useState(false);
  const [defaultOption, setDefaultOption] = useState(menuOptions.CREATE);

  useEffect(() => {
    clearEvents();
  }, [defaultOption]);

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
            key={menuOptions.CREATE}
            icon={<PlusOutlined />}
            onClick={() => setDefaultOption(menuOptions.CREATE)}
          >
            New event
          </Menu.Item>
          <Menu.Item
            id="listEventsOption"
            key={menuOptions.LIST}
            icon={<AlignLeftOutlined />}
            onClick={() => setDefaultOption(menuOptions.LIST)}
          >
            List events
          </Menu.Item>
        </Menu>
      </Sider>
      <Layout className="site-layout">
        <Content className="content">
          {defaultOption === menuOptions.CREATE ? <NewEventForm /> : <Events />}
        </Content>
        <Footer id="footer" className="footer">
          ©2022 Created by Thomas Perron
        </Footer>
      </Layout>
    </Layout>
  );
};

export default App;
