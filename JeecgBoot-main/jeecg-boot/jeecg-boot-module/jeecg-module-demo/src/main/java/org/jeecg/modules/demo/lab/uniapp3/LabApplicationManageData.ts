import { render } from '@/common/renderUtils';
//列表数据
export const columns = [
    {
    title: '用户ID',
    align:"center",
    dataIndex: 'userId_dictText'
   },
   {
    title: '设备ID',
    align:"center",
    dataIndex: 'equipmentId_dictText'
   },
   {
    title: '项目ID',
    align:"center",
    dataIndex: 'projectId_dictText'
   },
   {
    title: '开始日期',
    align:"center",
    dataIndex: 'startDate',
   },
   {
    title: '结束日期',
    align:"center",
    dataIndex: 'endDate',
   },
   {
    title: '用途或项目内容',
    align:"center",
    dataIndex: 'purpose'
   },
   {
    title: '申请状态',
    align:"center",
    dataIndex: 'status_dictText'
   },
];