import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '设备编号',
    align:"center",
    dataIndex: 'equipmentCode'
   },
   {
    title: '设备名称',
    align:"center",
    dataIndex: 'equipmentName'
   },
   {
    title: '设备状态',
    align:"center",
    dataIndex: 'status_dictText'
   },
   {
    title: '所属组',
    align:"center",
    dataIndex: 'groupId_dictText'
   },
];